package com.example.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) para operaciones reactivas sobre la tabla de alabanzas.
 */
@Dao
interface HymnDao {

    @Query("SELECT * FROM hymns WHERE isDeleted = 0 ORDER BY id ASC")
    fun getAllActiveHymns(): Flow<List<HymnEntity>>

    @Query("SELECT * FROM hymns WHERE id = :id LIMIT 1")
    suspend fun getHymnById(id: Int): HymnEntity?

    @Query("SELECT isFavorite FROM hymns WHERE id = :id LIMIT 1")
    suspend fun isFavorite(id: Int): Boolean?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertHymns(hymns: List<HymnEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertHymn(hymn: HymnEntity)

    @Query("UPDATE hymns SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavorite(id: Int, isFavorite: Boolean)

    @Query("UPDATE hymns SET isDeleted = 1 WHERE id = :id")
    suspend fun markDeleted(id: Int)

    @Query("SELECT COUNT(*) FROM hymns WHERE isDeleted = 0")
    suspend fun getActiveHymnCount(): Int

    @Query("SELECT id FROM hymns WHERE isFavorite = 1 AND isDeleted = 0")
    fun getFavoriteHymnIds(): Flow<List<Int>>
}
