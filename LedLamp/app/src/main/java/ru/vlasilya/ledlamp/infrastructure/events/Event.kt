package ru.vlasilya.ledlamp.infrastructure.events

import ru.vlasilya.ledlamp.domain.model.Settings

sealed class Event()

class LampWasUpdatedEvent(): Event();
class SettingsWereChangedEvent(val newSettings: Settings): Event();
