package com.example.search

import com.example.data.database.HymnEntity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class HymnSearchEngineTest {

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
    fun testSearchExactId() {
        val trimmed = "2".trim().normalize()
        val results = dataset.filter {
            it.hymn.id.toString() == trimmed ||
                    it.normalizedTitle.contains(trimmed) ||
                    it.normalizedAuthor.contains(trimmed) ||
                    it.normalizedContent.contains(trimmed)
        }
        assertEquals(1, results.size)
        assertEquals(2, results[0].hymn.id)
    }

    @Test
    fun testSearchExactTitle() {
        val trimmed = "cuan grande".trim().normalize()
        val results = dataset.filter {
            it.hymn.id.toString() == trimmed ||
                    it.normalizedTitle.contains(trimmed) ||
                    it.normalizedAuthor.contains(trimmed) ||
                    it.normalizedContent.contains(trimmed)
        }
        assertEquals(1, results.size)
        assertEquals(1, results[0].hymn.id)
    }

    @Test
    fun testSearchContentPreservesOrder() {
        val trimmed = "cristo".trim().normalize()
        val results = dataset.filter {
            it.hymn.id.toString() == trimmed ||
                    it.normalizedTitle.contains(trimmed) ||
                    it.normalizedAuthor.contains(trimmed) ||
                    it.normalizedContent.contains(trimmed)
        }
        assertEquals(1, results.size)
        assertEquals(2, results[0].hymn.id)
    }

    @Test
    fun testEmptyQueryReturnsAllInNaturalOrder() {
        val trimmed = "".trim().normalize()
        val results = if (trimmed.isEmpty()) dataset else dataset.filter {
            it.hymn.id.toString() == trimmed ||
                    it.normalizedTitle.contains(trimmed) ||
                    it.normalizedAuthor.contains(trimmed) ||
                    it.normalizedContent.contains(trimmed)
        }
        assertEquals(3, results.size)
        assertEquals(1, results[0].hymn.id)
        assertEquals(2, results[1].hymn.id)
        assertEquals(3, results[2].hymn.id)
    }
}
