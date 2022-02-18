package ru.vlasilya.ledlamp.infrastructure.services.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.vlasilya.ledlamp.domain.model.Settings
import ru.vlasilya.ledlamp.infrastructure.events.Event
import ru.vlasilya.ledlamp.infrastructure.events.EventDispatcher
import ru.vlasilya.ledlamp.infrastructure.repositories.SettingsRepository
import ru.vlasilya.ledlamp.infrastructure.services.SettingsService

class SettingsServiceImpl(
    private val settingsRepository: SettingsRepository,
    private val eventDispatcher: EventDispatcher
) : SettingsService {
    override fun <T> get(method: (Settings) -> T): T {
        val settings: Settings = settingsRepository.getSettings()
        return method(settings)
    }

    override suspend fun run(method: (Settings) -> Unit) {
        val settings: Settings = settingsRepository.getSettings()
        method(settings)
        settingsRepository.saveSettings(settings)
        withContext(Dispatchers.IO) {
            launch {
                val events: List<Event> = settings.events()
                events.forEach { eventDispatcher.dispatch(it) }
            }
        }
    }
}
