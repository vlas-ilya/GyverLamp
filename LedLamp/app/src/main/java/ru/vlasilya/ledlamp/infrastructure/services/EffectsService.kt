package ru.vlasilya.ledlamp.infrastructure.services

import ru.vlasilya.ledlamp.domain.model.Effect

interface EffectsService {
    fun list(): List<Effect>
    fun update(effect: Effect)
}
