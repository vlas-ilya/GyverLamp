package ru.vlasilya.ledlamp.infrastructure.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.vlasilya.ledlamp.infrastructure.dao.entities.FavoriteEffectEntity

@Database(entities = [FavoriteEffectEntity::class], version = 1, exportSchema = false)
abstract class FavoriteEffectsDatabase : RoomDatabase() {

    abstract val favoriteEffectsDao: FavoriteEffectsDao

    companion object {
        @Volatile
        private var INSTANCE: FavoriteEffectsDatabase? = null

        fun getInstance(context: Context): FavoriteEffectsDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FavoriteEffectsDatabase::class.java,
                        "favorite_effects_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }

                return instance;
            }
        }
    }
}