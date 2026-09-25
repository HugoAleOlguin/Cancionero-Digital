package com.example.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.FavoriteHymn
import com.example.data.FavoriteHymnDao

/**
 * Base de datos principal Room de la aplicación.
 * Aloja tanto el catálogo completo de alabanzas sincronizables como la persistencia de favoritos.
 */
@Database(
    entities = [HymnEntity::class, FavoriteHymn::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun hymnDao(): HymnDao
    abstract fun favoriteHymnDao(): FavoriteHymnDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "cancionero_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
