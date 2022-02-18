package ru.vlasilya.ledlamp.infrastructure.services

import ru.vlasilya.ledlamp.domain.model.Effect
import ru.vlasilya.ledlamp.domain.model.FavoriteEffect

interface FavoriteEffectsService {
    fun add(effect: Effect)
    fun list(): List<FavoriteEffect>
    fun delete(id: Long)
}