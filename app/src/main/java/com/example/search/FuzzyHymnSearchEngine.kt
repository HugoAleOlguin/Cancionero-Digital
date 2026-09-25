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
 * Representación en memoria de la alabanza optimizada para búsquedas de latencia cero.
 */
data class SearchableHymn(
    val hymn: HymnEntity,
    val normalizedTitle: String,
    val normalizedAuthor: String,
    val normalizedContent: String,
    val splitStanzas: List<String>,
    val normalizedStanzas: List<String>,
    val stanzaWordLists: List<List<String>>
) {
    companion object {
        fun from(hymn: HymnEntity): SearchableHymn {
            val displayTitle = "${hymn.id} - ${hymn.title}"
            val normTitle = displayTitle.normalize()
            val normAuthor = hymn.author.normalize()
            val allVersions = hymn.getVersions()
            val combinedContent = allVersions.joinToString("\n\n")
            val normContent = combinedContent.normalize()
            val stanzas = hymn.content.split("\n\n")
            val normStanzas = stanzas.map { it.normalize() }
            val wordLists = normStanzas.map { s ->
                s.split(Regex("[^a-z0-9ñ]+")).filter { it.isNotBlank() }
            }

            return SearchableHymn(
                hymn = hymn,
                normalizedTitle = normTitle,
                normalizedAuthor = normAuthor,
                normalizedContent = normContent,
                splitStanzas = stanzas,
                normalizedStanzas = normStanzas,
                stanzaWordLists = wordLists
            )
        }
    }
}

/**
 * Resultado de una búsqueda con su puntuación de relevancia y ocurrencias para resaltado.
 */
data class SearchResult(
    val hymn: SearchableHymn,
    val rankScore: Int,
    val occurrences: List<MatchOccurrence>
)

/**
 * Motor de búsqueda difuso (Fuzzy) y multi-palabra de latencia cero (0ms).
 * Implementa distancia Damerau-Levenshtein para soportar inserciones, eliminaciones,
 * sustituciones y transposiciones de letras adyacentes al escribir apurado.
 */
object FuzzyHymnSearchEngine {

    /**
     * Calcula coincidencia difusa con tolerancia a 1 error de tipeo (Damerau-Levenshtein).
     */
    fun isFuzzyMatch(token: String, word: String, maxDistance: Int = 1): Boolean {
        if (token == word) return true
        val lenT = token.length
        val lenW = word.length
        if (Math.abs(lenT - lenW) > maxDistance) return false
        // No aplicar fuzzy en palabras muy cortas (1-3 letras) como "de", "el", "la"
        if (lenT < 4 || lenW < 4) return false

        val d = Array(lenT + 1) { IntArray(lenW + 1) }

        for (i in 0..lenT) d[i][0] = i
        for (j in 0..lenW) d[0][j] = j

        for (i in 1..lenT) {
            for (j in 1..lenW) {
                val cost = if (token[i - 1] == word[j - 1]) 0 else 1
                d[i][j] = minOf(
                    d[i - 1][j] + 1,       // Eliminación
                    d[i][j - 1] + 1,       // Inserción
                    d[i - 1][j - 1] + cost // Sustitución
                )
                // Transposición de caracteres adyacentes (error típico de teclado en móviles)
                if (i > 1 && j > 1 && token[i - 1] == word[j - 2] && token[i - 2] == word[j - 1]) {
                    d[i][j] = minOf(d[i][j], d[i - 2][j - 2] + 1)
                }
            }
        }
        return d[lenT][lenW] <= maxDistance
    }

    /**
     * Ejecuta la búsqueda sobre el conjunto de alabanzas indexadas en memoria.
     */
    fun search(dataset: List<SearchableHymn>, rawQuery: String): List<SearchResult> {
        val trimmed = rawQuery.trim().normalize()
        if (trimmed.isEmpty()) {
            return dataset.map { SearchResult(it, 0, emptyList()) }
        }

        val idQuery = trimmed.toIntOrNull()
        val tokens = trimmed.split(Regex("[^a-z0-9ñ]+")).filter { it.isNotBlank() }

        val results = mutableListOf<SearchResult>()

        for (item in dataset) {
            var score = 0
            val occurrences = mutableListOf<MatchOccurrence>()

            // 1. Coincidencia exacta de ID (ej. "45")
            if (idQuery != null && item.hymn.id == idQuery) {
                score += 1000
                occurrences.add(MatchOccurrence(item.hymn.id, isTitle = true, stanzaIndex = -1, charRange = 0..item.normalizedTitle.length))
            }

            // 2. Coincidencia exacta de subcadena en título
            val titleIndex = item.normalizedTitle.indexOf(trimmed)
            if (titleIndex != -1) {
                score += 300
                occurrences.add(MatchOccurrence(item.hymn.id, isTitle = true, stanzaIndex = -1, charRange = titleIndex until (titleIndex + trimmed.length)))
            } else if (tokens.size > 1 && tokens.all { item.normalizedTitle.contains(it) }) {
                score += 200
            }

            // 3. Coincidencia exacta de frase en estrofas
            var exactPhraseInContent = false
            for (sIdx in item.normalizedStanzas.indices) {
                val stanza = item.normalizedStanzas[sIdx]
                var matchPos = stanza.indexOf(trimmed)
                while (matchPos != -1) {
                    exactPhraseInContent = true
                    occurrences.add(
                        MatchOccurrence(
                            hymnId = item.hymn.id,
                            isTitle = false,
                            stanzaIndex = sIdx,
                            charRange = matchPos until (matchPos + trimmed.length)
                        )
                    )
                    matchPos = stanza.indexOf(trimmed, matchPos + 1)
                }
            }
            if (exactPhraseInContent) {
                score += 150
            }

            // 4. Coincidencia multi-palabra y difusa en estrofas
            if (!exactPhraseInContent && tokens.isNotEmpty()) {
                var bestStanzaScore = 0
                for (sIdx in item.normalizedStanzas.indices) {
                    val words = item.stanzaWordLists[sIdx]
                    var matchedTokensCount = 0

                    for (token in tokens) {
                        val matchedWord = words.firstOrNull { w ->
                            w == token || w.contains(token) || isFuzzyMatch(token, w, 1)
                        }
                        if (matchedWord != null) {
                            matchedTokensCount++
                            val wordPos = item.normalizedStanzas[sIdx].indexOf(matchedWord)
                            if (wordPos != -1) {
                                occurrences.add(
                                    MatchOccurrence(
                                        hymnId = item.hymn.id,
                                        isTitle = false,
                                        stanzaIndex = sIdx,
                                        charRange = wordPos until (wordPos + matchedWord.length)
                                    )
                                )
                            }
                        }
                    }

                    if (matchedTokensCount == tokens.size) {
                        bestStanzaScore = maxOf(bestStanzaScore, 100)
                    } else if (matchedTokensCount > 0) {
                        bestStanzaScore = maxOf(bestStanzaScore, matchedTokensCount * 25)
                    }
                }
                score += bestStanzaScore
            }

            if (score > 0) {
                results.add(SearchResult(item, score, occurrences))
            }
        }

        results.sortWith(compareByDescending<SearchResult> { it.rankScore }.thenBy { it.hymn.hymn.id })
        return results
    }
}
