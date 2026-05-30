package com.example.data

import kotlinx.coroutines.flow.Flow

class FavoriteRepository(private val favoriteHymnDao: FavoriteHymnDao) {
    val favoriteHymnIds: Flow<List<Int>> = favoriteHymnDao.getFavoriteHymnIds()

    suspend fun addFavorite(hymnId: Int) {
        favoriteHymnDao.insertFavorite(FavoriteHymn(hymnId))
    }

    suspend fun removeFavorite(hymnId: Int) {
        favoriteHymnDao.deleteFavoriteById(hymnId)
    }
}
