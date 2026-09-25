package com.example.data.repository

import android.content.Context
import android.util.Log
import com.example.data.FavoriteHymn
import com.example.data.FavoriteHymnDao
import com.example.data.database.HymnDao
import com.example.data.database.HymnEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Repositorio central que actúa como Fuente Única de Verdad (Single Source of Truth)
 * para todas las alabanzas y favoritos de la aplicación.
 */
class HymnRepository(
    private val hymnDao: HymnDao,
    private val favoriteHymnDao: FavoriteHymnDao,
    private val context: Context
) {
    private val tag = "HymnRepository"

    val allHymns: Flow<List<HymnEntity>> = hymnDao.getAllActiveHymns()
    val favoriteHymnIds: Flow<List<Int>> = favoriteHymnDao.getFavoriteHymnIds()

    /**
     * Asegura que la base de datos local contenga el catálogo base.
     * Si la base de datos está vacía (primer arranque), la pre-popula instantáneamente
     * desde el archivo `assets/catalog.json` sin requerir conexión a internet.
     */
    suspend fun ensureInitialized() = withContext(Dispatchers.IO) {
        val count = hymnDao.getActiveHymnCount()
        if (count == 0) {
            Log.d(tag, "Base de datos vacía. Pre-populando desde assets/catalog.json...")
            try {
                val hymns = loadCatalogFromAssets()
                if (hymns.isNotEmpty()) {
                    hymnDao.upsertHymns(hymns)
                    Log.d(tag, "Pre-población completada exitosamente con ${hymns.size} alabanzas.")
                }
            } catch (e: Exception) {
                Log.e(tag, "Error al pre-popular catálogo desde assets", e)
            }
        }
    }

    /**
     * Lee y parsea `assets/catalog.json` de manera segura usando `org.json`.
     */
    private fun loadCatalogFromAssets(): List<HymnEntity> {
        val inputStream = context.assets.open("catalog.json")
        val reader = BufferedReader(InputStreamReader(inputStream, Charsets.UTF_8))
        val jsonString = reader.use { it.readText() }
        val root = JSONObject(jsonString)
        val hymnsArray = root.getJSONArray("hymns")

        val result = mutableListOf<HymnEntity>()
        for (i in 0 until hymnsArray.length()) {
            val obj = hymnsArray.getJSONObject(i)
            val isDeleted = obj.optBoolean("isDeleted", false)
            if (!isDeleted) {
                result.add(
                    HymnEntity(
                        id = obj.getInt("id"),
                        title = obj.getString("title"),
                        author = obj.optString("author", ""),
                        link = obj.optString("link", ""),
                        content = obj.getString("content"),
                        isFavorite = false,
                        updatedAt = obj.optString("updatedAt", ""),
                        isDeleted = false,
                        versionsJson = when {
                            obj.has("extraVersions") -> obj.getJSONArray("extraVersions").toString()
                            obj.has("versions") -> obj.getJSONArray("versions").toString()
                            else -> null
                        }
                    )
                )
            }
        }
        return result
    }

    /**
     * Alterna el estado de favorito de una alabanza.
     */
    suspend fun toggleFavorite(hymnId: Int) = withContext(Dispatchers.IO) {
        val currentFavorites = favoriteHymnDao.getFavoriteHymnIds().first().toSet()
        val isFav = currentFavorites.contains(hymnId)
        if (isFav) {
            favoriteHymnDao.deleteFavoriteById(hymnId)
            hymnDao.updateFavorite(hymnId, false)
        } else {
            favoriteHymnDao.insertFavorite(FavoriteHymn(hymnId))
            hymnDao.updateFavorite(hymnId, true)
        }
    }

    suspend fun upsertHymns(hymns: List<HymnEntity>) = withContext(Dispatchers.IO) {
        hymnDao.upsertHymns(hymns)
    }

    suspend fun getHymnById(id: Int): HymnEntity? = withContext(Dispatchers.IO) {
        hymnDao.getHymnById(id)
    }
}
