package ru.vlasilya.ledlamp.infrastructure.network

import ru.vlasilya.ledlamp.domain.model.Command

interface NetworkFacade {
    suspend fun send(commands: List<Command>)
    suspend fun getLampInfo(): LampInfo?
}