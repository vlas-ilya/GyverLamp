package ru.vlasilya.ledlamp.infrastructure.repositories

import ru.vlasilya.ledlamp.domain.model.Effect
import ru.vlasilya.ledlamp.domain.model.FavoriteEffect

interface FavoriteEffectsRepository {
    fun add(effect: Effect)
    fun list(): List<FavoriteEffect>
    fun delete(id: Long)
}