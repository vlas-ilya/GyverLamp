package ru.vlasilya.ledlamp.domain.model

import ru.vlasilya.ledlamp.infrastructure.events.Event
import ru.vlasilya.ledlamp.infrastructure.events.SettingsWereChangedEvent

class Settings(
    private var ipAddress: String,
    private var port: Int,
) {
    private val events: MutableList<Event> = ArrayList()
    fun events(): List<Event> {
        val events = ArrayList<Event>(this.events)
        this.events.clear()
        return events
    }

    fun getIpAddress(): String {
        return ipAddress
    }

    fun getPort(): Int {
        return port
    }

    fun setIpAddress(ipAddress: String) {
        if (this.ipAddress != ipAddress) {
            this.ipAddress = ipAddress
            events.add(SettingsWereChangedEvent(this))
        }
    }

    fun setPort(port: Int) {
        if (this.port != port) {
            this.port = port
            events.add(SettingsWereChangedEvent(this))
        }
    }
}