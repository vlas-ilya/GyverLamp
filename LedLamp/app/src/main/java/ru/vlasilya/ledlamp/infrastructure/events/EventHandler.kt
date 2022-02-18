package ru.vlasilya.ledlamp.infrastructure.events

fun interface EventHandler {
    suspend fun handle(event: Event);
}