package ru.vlasilya.ledlamp.infrastructure.network.impl

import kotlinx.coroutines.*
import ru.vlasilya.ledlamp.domain.model.*
import ru.vlasilya.ledlamp.infrastructure.events.EventDispatcher
import ru.vlasilya.ledlamp.infrastructure.events.SettingsWereChangedEvent
import ru.vlasilya.ledlamp.infrastructure.network.LampInfo
import ru.vlasilya.ledlamp.infrastructure.network.NetworkFacade
import ru.vlasilya.ledlamp.infrastructure.services.SettingsService
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.util.concurrent.Executors

class NetworkFacadeImpl(
    settingsService: SettingsService,
    eventDispatcher: EventDispatcher
) : NetworkFacade {
    private var settings: Settings
    private var querying: Boolean = false

    init {
        this.settings = settingsService.get { it }
        eventDispatcher.subscribe {
            if (it is SettingsWereChangedEvent) {
                settings = it.newSettings
            }
        }
    }

    override suspend fun send(commands: List<Command>) {
        commands.forEach { sendCommand(it) }
    }

    override suspend fun getLampInfo(): LampInfo? {
        try {
            val baseInfo = sendCommand(Get()) ?: return null
            val timerInfo = sendCommand(TimerGet()) ?: return null
            val alarmInfo = sendCommand(AlarmGet()) ?: return null
            val favoriteInfo = sendCommand(CarouselGet()) ?: return null
            return LampInfo(baseInfo, timerInfo, alarmInfo, favoriteInfo)
        } catch (t: Throwable) {
            return null
        }
    }

    private val dispatcher = Executors.newFixedThreadPool(2).asCoroutineDispatcher()

    private suspend fun sendCommand(command: Command): String? {
        while (querying) {
            delay(100)
        }
        querying = true
        var datagramSocket: DatagramSocket? = null
        return try {
            datagramSocket = withContext(dispatcher) { DatagramSocket(settings.getPort()) }
            return withContext(dispatcher) { sendCommand(datagramSocket, command, 3) }
        } catch (e: Throwable) {
            null
        } finally {
            datagramSocket?.close()
            querying = false
        }
    }

    private fun sendCommand(datagramSocket: DatagramSocket, command: Command, attempt: Int): String? {
        return try {
            println(command.toString())
            val serverAddress = InetAddress.getByName(settings.getIpAddress())
            val buf = command.toString().toByteArray()
            val requestPacket = DatagramPacket(buf, buf.size, serverAddress, settings.getPort())
            datagramSocket.send(requestPacket)

            val message = ByteArray(8000)
            val responsePacket = DatagramPacket(message, message.size)
            datagramSocket.soTimeout = 1000
            datagramSocket.receive(responsePacket)
            val result = String(message, 0, responsePacket.length)
            println(result)
            result
        } catch (e: Throwable) {
            if (attempt == 0) return null
            sendCommand(datagramSocket, command, attempt - 1)
        }
    }
}
