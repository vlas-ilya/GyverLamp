package ru.vlasilya.ledlamp.infrastructure.repositories

import ru.vlasilya.ledlamp.domain.model.Settings

interface SettingsRepository {
    fun getSettings(): Settings
    fun saveSettings(settings: Settings)
}