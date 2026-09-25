package com.example.search

import com.example.data.database.HymnEntity
import java.text.Normalizer
import java.util.regex.Pattern

/**
 * Normaliza cadenas de texto eliminando marcas diacríticas (acentos) y convirtiendo a minúsculas.
 */
fun String.normalize(): String {
    val temp = Normalizer.normalize(this, Normalizer.Form.NFD)
    val pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
    return pattern.matcher(temp).replaceAll("").lowercase()
}

/**
 * Representa una coincidencia exacta de término en título o estrofa para scroll y resaltado visual.
 */
data class MatchOccurrence(
    val hymnId: Int,
    val isTitle: Boolean,
    val stanzaIndex: Int, // -1 si es en título
    val charRange: IntRange
)

/**
 * Representación en memoria de la alabanza optimizada para búsquedas instantáneas simples de latencia cero.
 */
data class SearchableHymn(
    val hymn: HymnEntity,
    val normalizedTitle: String,
    val normalizedAuthor: String,
    val normalizedContent: String,
    val splitStanzas: List<String>
) {
    companion object {
        fun from(hymn: HymnEntity): SearchableHymn {
            val displayTitle = "${hymn.id} - ${hymn.title}"
            val allVersions = hymn.getVersions()
            val combinedContent = allVersions.joinToString("\n\n")
            return SearchableHymn(
                hymn = hymn,
                normalizedTitle = displayTitle.normalize(),
                normalizedAuthor = hymn.author.normalize(),
                normalizedContent = combinedContent.normalize(),
                splitStanzas = hymn.content.split("\n\n")
            )
        }
    }
}
