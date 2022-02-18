package ru.vlasilya.ledlamp.infrastructure.services.impl

import ru.vlasilya.ledlamp.domain.model.Lamp
import ru.vlasilya.ledlamp.infrastructure.repositories.LampRepository
import ru.vlasilya.ledlamp.infrastructure.events.EventDispatcher
import ru.vlasilya.ledlamp.infrastructure.events.LampWasUpdatedEvent
import ru.vlasilya.ledlamp.infrastructure.services.SynchroniseLampService

class SynchroniseLampServiceImpl(
    private val lampRepository: LampRepository,
    private val eventDispatcher: EventDispatcher,
) : SynchroniseLampService {
    override fun synchronise(lamp: Lamp) {
        lamp.setIsOnline(true)
        lampRepository.save(lamp)
        eventDispatcher.dispatch(LampWasUpdatedEvent())
    }

    override fun lampIsOffline() {
        val lamp = lampRepository.load()
        lamp.setIsOnline(false)
        lampRepository.save(lamp)
        eventDispatcher.dispatch(LampWasUpdatedEvent())
    }
}