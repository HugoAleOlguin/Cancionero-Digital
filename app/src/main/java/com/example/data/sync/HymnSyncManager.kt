package com.example.data.sync

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.example.data.database.HymnEntity
import com.example.data.repository.HymnRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

sealed class SyncResult {
    object Idle : SyncResult()
    object Checking : SyncResult()
    object Offline : SyncResult()
    object UpToDate : SyncResult()
    data class Success(val version: Int, val updatedCount: Int) : SyncResult()
    data class Error(val message: String) : SyncResult()
}

/**
 * Gestor de sincronización silenciosa sobre Wi-Fi.
 * Sincroniza el catálogo de alabanzas contra GitHub Raw sin costo alguno ni mantenimiento de servidores.
 */
class HymnSyncManager(
    private val context: Context,
    private val hymnRepository: HymnRepository
) {
    private val tag = "HymnSyncManager"
    private val prefs = context.getSharedPreferences("HymnSyncPrefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_LOCAL_VERSION = "local_catalog_version"
        private const val VERSION_URL = "https://raw.githubusercontent.com/HugoAleOlguin/Cancionero-Digital/main/data/catalog_version.json"
        private const val CATALOG_URL = "https://raw.githubusercontent.com/HugoAleOlguin/Cancionero-Digital/main/data/catalog.json"
        private const val TIMEOUT_MS = 5000
    }

    fun getLocalVersion(): Int {
        return prefs.getInt(KEY_LOCAL_VERSION, 1)
    }

    private fun setLocalVersion(version: Int) {
        prefs.edit().putInt(KEY_LOCAL_VERSION, version).apply()
    }

    /**
     * Verifica si el dispositivo está conectado a una red Wi-Fi activa.
     */
    fun isConnectedToWifi(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager ?: return false
        val network = cm.activeNetwork ?: return false
        val capabilities = cm.getNetworkCapabilities(network) ?: return false
        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) &&
                capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    /**
     * Ejecuta una comprobación y sincronización en segundo plano si hay Wi-Fi disponible.
     * Retorna el resultado de la operación sin bloquear el hilo principal.
     */
    suspend fun syncIfWifiAvailable(force: Boolean = false): SyncResult = withContext(Dispatchers.IO) {
        if (!force && !isConnectedToWifi()) {
            Log.d(tag, "Dispositivo no conectado a Wi-Fi. Sincronización omitida.")
            return@withContext SyncResult.Offline
        }

        try {
            Log.d(tag, "Comprobando versión remota del catálogo en GitHub...")
            val versionJsonString = fetchUrl(VERSION_URL) ?: return@withContext SyncResult.Error("No se pudo obtener la versión remota.")
            val versionObj = JSONObject(versionJsonString)
            val remoteVersion = versionObj.getInt("version")
            val localVersion = getLocalVersion()

            Log.d(tag, "Versión local: v$localVersion | Versión remota: v$remoteVersion")

            if (!force && remoteVersion <= localVersion) {
                Log.d(tag, "El catálogo ya está actualizado.")
                return@withContext SyncResult.UpToDate
            }

            Log.d(tag, "Descargando nuevo catálogo v$remoteVersion desde GitHub...")
            val catalogJsonString = fetchUrl(CATALOG_URL) ?: return@withContext SyncResult.Error("No se pudo descargar el catálogo completo.")
            val catalogObj = JSONObject(catalogJsonString)
            val hymnsArray = catalogObj.getJSONArray("hymns")

            // Preservar favoritos locales
            val favoriteIds = hymnRepository.favoriteHymnIds.first().toSet()

            val entities = mutableListOf<HymnEntity>()
            for (i in 0 until hymnsArray.length()) {
                val obj = hymnsArray.getJSONObject(i)
                val id = obj.getInt("id")
                val isDeleted = obj.optBoolean("isDeleted", false)
                entities.add(
                    HymnEntity(
                        id = id,
                        title = obj.getString("title"),
                        author = obj.optString("author", ""),
                        link = obj.optString("link", ""),
                        content = obj.getString("content"),
                        isFavorite = favoriteIds.contains(id),
                        updatedAt = obj.optString("updatedAt", ""),
                        isDeleted = isDeleted,
                        versionsJson = when {
                            obj.has("extraVersions") -> obj.getJSONArray("extraVersions").toString()
                            obj.has("versions") -> obj.getJSONArray("versions").toString()
                            else -> null
                        }
                    )
                )
            }

            if (entities.isNotEmpty()) {
                hymnRepository.upsertHymns(entities)
                setLocalVersion(remoteVersion)
                Log.d(tag, "Sincronización completada exitosamente. v$remoteVersion con ${entities.size} registros.")
                return@withContext SyncResult.Success(remoteVersion, entities.size)
            }

            return@withContext SyncResult.UpToDate

        } catch (e: Exception) {
            Log.e(tag, "Error durante la sincronización", e)
            return@withContext SyncResult.Error(e.message ?: "Error desconocido de sincronización")
        }
    }

    private fun fetchUrl(urlString: String): String? {
        var connection: HttpURLConnection? = null
        return try {
            val url = URL(urlString)
            connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = TIMEOUT_MS
            connection.readTimeout = TIMEOUT_MS
            connection.useCaches = false

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val reader = BufferedReader(InputStreamReader(connection.inputStream, Charsets.UTF_8))
                reader.use { it.readText() }
            } else {
                Log.w(tag, "HTTP error: ${connection.responseCode} para $urlString")
                null
            }
        } catch (e: Exception) {
            Log.w(tag, "Fallo de conexión para $urlString: ${e.message}")
            null
        } finally {
            connection?.disconnect()
        }
    }
}
