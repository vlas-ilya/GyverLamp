package ru.vlasilya.ledlamp.infrastructure.services.impl

import ru.vlasilya.ledlamp.domain.model.Lamp
import ru.vlasilya.ledlamp.infrastructure.repositories.LampRepository
import ru.vlasilya.ledlamp.infrastructure.services.LampService
import ru.vlasilya.ledlamp.infrastructure.network.NetworkFacade

class LampServiceImpl(
    private val lampRepository: LampRepository,
    private val networkFacade: NetworkFacade,
    private val lock: OptimisticLock,
) : LampService {

    override fun <T> get(method: (Lamp) -> T): T {
        val lamp = lampRepository.load()
        return method(lamp)
    }

    override suspend fun run(method: (Lamp) -> Unit) {
        synchronized(lock) { lock.used = true }
        val lamp = lampRepository.load()
        method(lamp)
        lampRepository.save(lamp)
        networkFacade.send(lamp.pullCommands())
    }
}
