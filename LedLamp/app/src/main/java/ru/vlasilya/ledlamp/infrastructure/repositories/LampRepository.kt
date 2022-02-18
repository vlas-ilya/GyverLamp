package ru.vlasilya.ledlamp.infrastructure.repositories

import ru.vlasilya.ledlamp.domain.model.Lamp

interface LampRepository {
    fun load(): Lamp
    fun save(lamp: Lamp)
}