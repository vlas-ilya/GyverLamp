package ru.vlasilya.ledlamp.infrastructure.services.impl

import kotlinx.coroutines.*
import ru.vlasilya.ledlamp.domain.utils.*
import ru.vlasilya.ledlamp.infrastructure.network.NetworkFacade
import ru.vlasilya.ledlamp.infrastructure.services.LampPullService
import ru.vlasilya.ledlamp.infrastructure.services.SynchroniseLampService

class LampPullServiceImpl(
    private val networkFacade: NetworkFacade,
    private val synchroniseLampService: SynchroniseLampService,
    private val lock: OptimisticLock,
) : LampPullService {
    private var pull = false

    override fun startPulling() {
        pull = true
        while (pull) {
            runBlocking {
                launch {
                    pull()
                    delay(900)
                }
            }
        }
    }

    override fun stopPulling() {
        pull = false
    }

    private fun pull() {
        runBlocking(Dispatchers.IO) {
            synchronized(lock) { lock.used = false }
            val info = networkFacade.getLampInfo()
            if (info == null) {
                synchroniseLampService.lampIsOffline()
                return@runBlocking
            }
            val lamp = makeLampFromLampInfo(info) ?: return@runBlocking
            delay(100)
            synchronized(lock) {
                if (lock.used) {
                    return@runBlocking
                }
            }
            synchroniseLampService.synchronise(lamp)
        }
    }
}
