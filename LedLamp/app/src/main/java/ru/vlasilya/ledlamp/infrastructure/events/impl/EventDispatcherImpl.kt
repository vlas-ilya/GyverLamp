package ru.vlasilya.ledlamp.infrastructure.events.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import ru.vlasilya.ledlamp.infrastructure.events.*
import java.util.*
import kotlin.collections.HashMap

class EventDispatcherImpl : EventDispatcher {
    private val handlers: MutableMap<String, EventHandler> = HashMap()

    override fun subscribe(handler: EventHandler): Disposable {
        val id = UUID.randomUUID().toString()
        synchronized(handlers) {
            handlers[id] = handler
        }
        return Disposable { handlers.remove(id) }
    }

    private fun handlers(): Collection<EventHandler> {
        synchronized(handlers) {
            return handlers.values
        }
    }

    override fun dispatch(event: Event) {
        val handlers = handlers()
        runBlocking(Dispatchers.IO) {
            handlers.forEach {
                launch { it.handle(event) }
            }
        }
    }
}
