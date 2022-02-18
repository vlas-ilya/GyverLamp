package ru.vlasilya.ledlamp.infrastructure.events

interface EventDispatcher {
    fun subscribe(handler: EventHandler): Disposable
    fun dispatch(event: Event)
}