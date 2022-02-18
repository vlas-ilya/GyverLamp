package ru.vlasilya.ledlamp.infrastructure.services

import ru.vlasilya.ledlamp.domain.model.Lamp

interface LampService {
    fun <T> get(method: (Lamp) -> T): T
    suspend fun run(method: (Lamp) -> Unit)
}