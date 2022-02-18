package ru.vlasilya.ledlamp.infrastructure.dao

import androidx.room.*
import ru.vlasilya.ledlamp.infrastructure.dao.entities.FavoriteEffectEntity

@Dao
interface FavoriteEffectsDao {
    @Insert
    fun insert(effect: FavoriteEffectEntity)

    @Update
    fun update(effect: FavoriteEffectEntity)

    @Query("SELECT * from favorite_effects WHERE id = :key")
    fun get(key: Long): FavoriteEffectEntity?

    @Query("SELECT * from favorite_effects")
    fun list(): List<FavoriteEffectEntity>

    @Delete
    fun delete(entity: FavoriteEffectEntity)
}