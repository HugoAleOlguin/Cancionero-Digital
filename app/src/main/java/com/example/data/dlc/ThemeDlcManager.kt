package com.example.data.dlc

import android.content.Context
import android.util.Log
import com.example.search.normalize
import com.example.ui.theme.ThemeMode
import com.example.util.ModernImageLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

sealed class DlcStatus {
    object NotDownloaded : DlcStatus()
    data class Downloading(val progress: Float) : DlcStatus()
    object Downloaded : DlcStatus()
    data class Error(val message: String) : DlcStatus()
}

/**
 * Gestor de Contenido Descargable (DLC) para el Tema Moderno.
 * Permite descargar, descomprimir y asociar las portadas HD a las 485 alabanzas
 * sin sobrecargar el APK base, respetando la filosofía offline-first.
 */
class ThemeDlcManager(private val context: Context) {

    private val tag = "ThemeDlcManager"
    private val prefs = context.getSharedPreferences("HymnThemeDlcPrefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_THEME_MODE = "active_theme_mode"
        private const val KEY_DLC_DOWNLOADED = "is_dlc_installed"
        private const val DLC_PACK_URL = "https://raw.githubusercontent.com/HugoAleOlguin/Cancionero-Digital/main/data/dlc/modern_theme_v1.zip"
        private const val TIMEOUT_MS = 15000
    }

    val dlcFolder = File(context.filesDir, "dlc/modern_theme")
    val coversFolder = File(dlcFolder, "covers")
    private val manifestFile = File(dlcFolder, "theme_manifest.json")

    private val _dlcStatus = MutableStateFlow<DlcStatus>(
        if (isInstalled()) DlcStatus.Downloaded else DlcStatus.NotDownloaded
    )
    val dlcStatus = _dlcStatus.asStateFlow()

    private val _themeMode = MutableStateFlow(loadInitialThemeMode())
    val themeMode = _themeMode.asStateFlow()

    // Estructuras en memoria del manifiesto para mapeo ultrarrápido (0ms)
    private var hymnOverrides: Map<Int, String> = emptyMap()
    private var authorOverrides: Map<String, String> = emptyMap()
    private var keywordOverrides: Map<String, String> = emptyMap()
    private var defaultCovers: List<String> = emptyList()

    init {
        if (isInstalled()) {
            loadManifestInMemory()
        }
    }

    private fun loadInitialThemeMode(): ThemeMode {
        val saved = prefs.getString(KEY_THEME_MODE, ThemeMode.CLASSIC.name) ?: ThemeMode.CLASSIC.name
        return try {
            val mode = ThemeMode.valueOf(saved)
            if (mode == ThemeMode.MODERN && !isInstalled()) ThemeMode.CLASSIC else mode
        } catch (e: Exception) {
            ThemeMode.CLASSIC
        }
    }

    fun isInstalled(): Boolean {
        return prefs.getBoolean(KEY_DLC_DOWNLOADED, false) && manifestFile.exists() && coversFolder.exists()
    }

    fun setThemeMode(mode: ThemeMode) {
        if (mode == ThemeMode.MODERN && !isInstalled()) {
            return
        }
        _themeMode.value = mode
        prefs.edit().putString(KEY_THEME_MODE, mode.name).apply()
    }

    /**
     * Resuelve la ruta absoluta del archivo de portada para una alabanza dada.
     * Retorna null si el DLC no está instalado o no hay foto asignable.
     */
    fun getCoverPathForHymn(hymnId: Int, title: String, author: String): String? {
        if (!isInstalled()) return null

        // 1. Mapeo específico por ID de alabanza
        hymnOverrides[hymnId]?.let { filename ->
            val file = File(coversFolder, filename)
            if (file.exists()) return file.absolutePath
        }

        // 2. Mapeo por autor
        if (author.isNotBlank()) {
            authorOverrides[author]?.let { filename ->
                val file = File(coversFolder, filename)
                if (file.exists()) return file.absolutePath
            }
        }

        // 3. Mapeo por palabras clave del título
        val normalizedTitle = title.normalize().lowercase()
        for ((keyword, filename) in keywordOverrides) {
            if (normalizedTitle.contains(keyword.lowercase())) {
                val file = File(coversFolder, filename)
                if (file.exists()) return file.absolutePath
            }
        }

        // 4. Mapeo rotativo determinista por defecto
        if (defaultCovers.isNotEmpty()) {
            val index = Math.abs(hymnId.hashCode()) % defaultCovers.size
            val filename = defaultCovers[index]
            val file = File(coversFolder, filename)
            if (file.exists()) return file.absolutePath
        }

        return null
    }

    /**
     * Descarga el paquete ZIP de GitHub Raw/Releases, reporta progreso y descomprime en filesDir.
     */
    suspend fun downloadDlcPack(customUrl: String? = null): Boolean = withContext(Dispatchers.IO) {
        val downloadUrl = customUrl ?: DLC_PACK_URL
        _dlcStatus.value = DlcStatus.Downloading(0.05f)

        var connection: HttpURLConnection? = null
        try {
            Log.d(tag, "Iniciando descarga de paquete DLC desde: $downloadUrl")
            val url = URL(downloadUrl)
            connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = TIMEOUT_MS
            connection.readTimeout = TIMEOUT_MS
            connection.useCaches = false

            if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                _dlcStatus.value = DlcStatus.Error("Error HTTP ${connection.responseCode}")
                return@withContext false
            }

            val totalSize = connection.contentLength.toFloat()
            val tempZipFile = File(context.cacheDir, "temp_modern_theme.zip")

            connection.inputStream.use { input ->
                FileOutputStream(tempZipFile).use { output ->
                    val buffer = ByteArray(8192)
                    var bytesRead: Int
                    var totalRead = 0L

                    while (input.read(buffer).also { bytesRead = it } != -1) {
                        output.write(buffer, 0, bytesRead)
                        totalRead += bytesRead
                        if (totalSize > 0) {
                            val progress = 0.05f + (totalRead / totalSize) * 0.75f
                            _dlcStatus.value = DlcStatus.Downloading(progress.coerceAtMost(0.85f))
                        }
                    }
                    output.flush()
                }
            }

            // Descompresión del ZIP
            _dlcStatus.value = DlcStatus.Downloading(0.90f)
            unzipFile(tempZipFile, dlcFolder)
            tempZipFile.delete()

            // Cargar manifiesto en memoria
            loadManifestInMemory()

            prefs.edit().putBoolean(KEY_DLC_DOWNLOADED, true).apply()
            _dlcStatus.value = DlcStatus.Downloaded
            setThemeMode(ThemeMode.MODERN)
            Log.d(tag, "Paquete DLC instalado y activado con éxito.")
            return@withContext true

        } catch (e: Exception) {
            Log.e(tag, "Fallo durante la descarga del DLC", e)
            _dlcStatus.value = DlcStatus.Error(e.message ?: "Fallo de conexión al descargar el pack")
            return@withContext false
        } finally {
            connection?.disconnect()
        }
    }

    /**
     * Desinstala el DLC, borra los archivos locales y libera memoria.
     */
    suspend fun uninstallDlc(): Boolean = withContext(Dispatchers.IO) {
        try {
            setThemeMode(ThemeMode.CLASSIC)
            ModernImageLoader.clearCache()
            dlcFolder.deleteRecursively()
            prefs.edit().putBoolean(KEY_DLC_DOWNLOADED, false).apply()

            hymnOverrides = emptyMap()
            authorOverrides = emptyMap()
            keywordOverrides = emptyMap()
            defaultCovers = emptyList()

            _dlcStatus.value = DlcStatus.NotDownloaded
            Log.d(tag, "DLC desinstalado y espacio liberado.")
            return@withContext true
        } catch (e: Exception) {
            Log.e(tag, "Error al desinstalar DLC", e)
            return@withContext false
        }
    }

    private fun loadManifestInMemory() {
        if (!manifestFile.exists()) return
        try {
            val jsonText = manifestFile.readText(Charsets.UTF_8)
            val json = JSONObject(jsonText)

            // Overrides por ID
            val hymnsObj = json.optJSONObject("hymnOverrides")
            if (hymnsObj != null) {
                val map = mutableMapOf<Int, String>()
                val keys = hymnsObj.keys()
                while (keys.hasNext()) {
                    val key = keys.next()
                    key.toIntOrNull()?.let { id ->
                        map[id] = hymnsObj.getString(key)
                    }
                }
                hymnOverrides = map
            }

            // Overrides por autor
            val authorsObj = json.optJSONObject("authorOverrides")
            if (authorsObj != null) {
                val map = mutableMapOf<String, String>()
                val keys = authorsObj.keys()
                while (keys.hasNext()) {
                    val key = keys.next()
                    map[key] = authorsObj.getString(key)
                }
                authorOverrides = map
            }

            // Overrides por palabras clave
            val keywordsObj = json.optJSONObject("keywordOverrides")
            if (keywordsObj != null) {
                val map = mutableMapOf<String, String>()
                val keys = keywordsObj.keys()
                while (keys.hasNext()) {
                    val key = keys.next()
                    map[key] = keywordsObj.getString(key)
                }
                keywordOverrides = map
            }

            // Fallbacks rotativos
            val defaultsArray = json.optJSONArray("defaultCovers")
            if (defaultsArray != null) {
                val list = mutableListOf<String>()
                for (i in 0 until defaultsArray.length()) {
                    list.add(defaultsArray.getString(i))
                }
                defaultCovers = list
            }

        } catch (e: Exception) {
            Log.e(tag, "Error al parsear theme_manifest.json", e)
        }
    }

    private fun unzipFile(zipFile: File, targetDir: File) {
        if (!targetDir.exists()) targetDir.mkdirs()

        ZipInputStream(BufferedInputStream(zipFile.inputStream())).use { zis ->
            var entry: ZipEntry? = zis.nextEntry
            val buffer = ByteArray(8192)

            while (entry != null) {
                val newFile = File(targetDir, entry.name)

                // Seguridad: prevenir Path Traversal Vulnerability
                if (!newFile.canonicalPath.startsWith(targetDir.canonicalPath)) {
                    throw SecurityException("Entrada ZIP fuera del directorio de destino: ${entry.name}")
                }

                if (entry.isDirectory) {
                    newFile.mkdirs()
                } else {
                    newFile.parentFile?.mkdirs()
                    FileOutputStream(newFile).use { fos ->
                        var len: Int
                        while (zis.read(buffer).also { len = it } > 0) {
                            fos.write(buffer, 0, len)
                        }
                    }
                }
                zis.closeEntry()
                entry = zis.nextEntry
            }
        }
    }
}
