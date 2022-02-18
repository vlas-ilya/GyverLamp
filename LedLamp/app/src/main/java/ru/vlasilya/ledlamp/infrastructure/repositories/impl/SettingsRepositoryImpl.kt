package ru.vlasilya.ledlamp.infrastructure.repositories.impl

import android.content.SharedPreferences
import ru.vlasilya.ledlamp.domain.model.Settings
import ru.vlasilya.ledlamp.infrastructure.repositories.SettingsRepository

class SettingsRepositoryImpl(
    private val storage: SharedPreferences
) : SettingsRepository {
    private val constants = object {
        val SETTINGS_IP_ADDRESS = "SETTINGS_IP_ADDRESS"
        val SETTINGS_PORT = "SETTINGS_PORT"
    }

    override fun getSettings(): Settings {
        val ipAddress = storage.getString(constants.SETTINGS_IP_ADDRESS, "") ?: ""
        val port = storage.getInt(constants.SETTINGS_PORT, 8888)
        return Settings(ipAddress, port)
    }

    override fun saveSettings(settings: Settings) {
        storage.edit()
            .putString(constants.SETTINGS_IP_ADDRESS, settings.getIpAddress())
            .putInt(constants.SETTINGS_PORT, settings.getPort())
            .apply()
    }
}