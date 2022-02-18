package ru.vlasilya.ledlamp.infrastructure.services

import ru.vlasilya.ledlamp.domain.model.Lamp

interface SynchroniseLampService {
    fun synchronise(lamp: Lamp)
    fun lampIsOffline()
}