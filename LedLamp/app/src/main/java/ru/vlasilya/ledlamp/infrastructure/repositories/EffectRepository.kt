package ru.vlasilya.ledlamp.infrastructure.repositories

import ru.vlasilya.ledlamp.domain.model.Effect

interface EffectRepository {
    fun list(): List<Effect>
    fun updateSettings(effect: Effect)
}