package ru.vlasilya.ledlamp.infrastructure.dao.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_effects")
data class FavoriteEffectEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long,

    @ColumnInfo(name = "effect")
    var effectId: Int,

    @ColumnInfo(name = "bright")
    val bright: Int,

    @ColumnInfo(name = "speed")
    val speed: Int,

    @ColumnInfo(name = "scale")
    val scale: Int,
)