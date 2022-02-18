package ru.vlasilya.ledlamp.infrastructure.services

import ru.vlasilya.ledlamp.domain.model.Settings

interface SettingsService {
    fun <T>get(method: (Settings) -> T): T
    suspend fun run(method: (Settings) -> Unit)
}