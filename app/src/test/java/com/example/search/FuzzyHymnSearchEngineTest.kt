package com.example.search

import com.example.data.database.HymnEntity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class FuzzyHymnSearchEngineTest {

    private lateinit var dataset: List<SearchableHymn>

    @Before
    fun setUp() {
        val h1 = HymnEntity(
            id = 1,
            title = "Cuan Grande Es Él",
            author = "Carl Boberg",
            content = "Señor mi Dios al contemplar los cielos\nEl firmamento y las mil estrellas.\n\nCoro:\nMi corazón entona la canción\n¡Cuán grande es Él!",
            isFavorite = false
        )
        val h2 = HymnEntity(
            id = 2,
            title = "En Una Nube Blanca",
            author = "Desconocido",
            content = "En una nube blanca Cristo volverá\nCon gran poder y gloria nos levantará.\n\nCoro:\nCristo viene ya, prepárate hermano.",
            isFavorite = true
        )
        val h3 = HymnEntity(
            id = 3,
            title = "La Barca",
            author = "Pescador",
            content = "En la orilla del mar de Galilea\nJesús llamaba a Pedro y Juan.",
            isFavorite = false
        )
        dataset = listOf(SearchableHymn.from(h1), SearchableHymn.from(h2), SearchableHymn.from(h3))
    }

    @Test
    fun testNormalize() {
        assertEquals("cuan grande es el", "Cuán Grande Es Él".normalize())
        assertEquals("senor", "Señor".normalize())
        assertEquals("galilea", "Galiléa".normalize())
    }

    @Test
    fun testFuzzyMatchFunction() {
        // Distancia 0 (idéntico)
        assertTrue(FuzzyHymnSearchEngine.isFuzzyMatch("grande", "grande", 1))

        // Transposición de letras adyacentes: "grnade" -> "grande"
        assertTrue(FuzzyHymnSearchEngine.isFuzzyMatch("grnade", "grande", 1))

        // Eliminación: "cielo" vs "cielos"
        assertTrue(FuzzyHymnSearchEngine.isFuzzyMatch("cielo", "cielos", 1))

        // Inserción: "cielos" vs "cielo"
        assertTrue(FuzzyHymnSearchEngine.isFuzzyMatch("cielos", "cielo", 1))

        // Sustitución de 1 letra: "blanda" vs "blanca"
        assertTrue(FuzzyHymnSearchEngine.isFuzzyMatch("blanda", "blanca", 1))

        // Distancia > 1 (demasiado diferente)
        assertFalse(FuzzyHymnSearchEngine.isFuzzyMatch("zapato", "cielos", 1))

        // Palabras cortas (<4 letras) no usan fuzzy para evitar falsos positivos
        assertFalse(FuzzyHymnSearchEngine.isFuzzyMatch("de", "el", 1))
    }

    @Test
    fun testSearchExactId() {
        val results = FuzzyHymnSearchEngine.search(dataset, "2")
        assertEquals(1, results.size)
        assertEquals(2, results[0].hymn.hymn.id)
    }

    @Test
    fun testSearchExactTitle() {
        val results = FuzzyHymnSearchEngine.search(dataset, "cuan grande")
        assertTrue(results.isNotEmpty())
        assertEquals(1, results[0].hymn.hymn.id)
    }

    @Test
    fun testSearchMultiWordNonConsecutiveInStanza() {
        // En h2: "En una nube blanca Cristo volverá... nos levantará"
        // Usuario escribe: "nube levantara" (palabras salteadas en la misma estrofa)
        val results = FuzzyHymnSearchEngine.search(dataset, "nube levantara")
        assertTrue(results.isNotEmpty())
        assertEquals(2, results[0].hymn.hymn.id)
    }

    @Test
    fun testSearchWithFuzzyTypo() {
        // Usuario escribe con error de 1 letra: "grnade" en lugar de "grande"
        val results = FuzzyHymnSearchEngine.search(dataset, "cuan grnade")
        assertTrue(results.isNotEmpty())
        assertEquals(1, results[0].hymn.hymn.id)
    }

    @Test
    fun testEmptyQueryReturnsAll() {
        val results = FuzzyHymnSearchEngine.search(dataset, "")
        assertEquals(3, results.size)
    }
}
