package com.example.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidad Room para representar una alabanza en la base de datos local SQLite.
 * Compatible con la sincronización remota desde catalog.json.
 */
@Entity(tableName = "hymns")
data class HymnEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val author: String = "",
    val link: String = "",
    val content: String,
    val isFavorite: Boolean = false,
    val updatedAt: String = "",
    val isDeleted: Boolean = false,
    val versionsJson: String? = null
) {
    /**
     * Retorna la lista de todas las versiones de la letra.
     * La primera versión es siempre el contenido canónico (content).
     * Si no hay versiones extra, retorna listOf(content).
     */
    fun getVersions(): List<String> {
        if (versionsJson.isNullOrBlank()) {
            return listOf(content)
        }
        return try {
            val array = org.json.JSONArray(versionsJson)
            val list = mutableListOf(content)
            for (i in 0 until array.length()) {
                val vStr = array.getString(i)
                if (vStr.isNotBlank() && vStr != content) {
                    list.add(vStr)
                }
            }
            list
        } catch (e: Exception) {
            listOf(content)
        }
    }
}
