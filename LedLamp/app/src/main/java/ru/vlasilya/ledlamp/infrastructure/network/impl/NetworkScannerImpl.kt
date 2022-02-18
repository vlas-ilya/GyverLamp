package ru.vlasilya.ledlamp.infrastructure.network.impl

import kotlinx.coroutines.*
import ru.vlasilya.ledlamp.infrastructure.network.NetworkScanner
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.NetworkInterface
import java.util.*


class NetworkScannerImpl : NetworkScanner {
    private fun currentIpAddress(): String? {
        try {
            val interfaces: List<NetworkInterface> = Collections.list(NetworkInterface.getNetworkInterfaces())
            for (networkInterface in interfaces) {
                val addresses: List<InetAddress> = Collections.list(networkInterface.inetAddresses)
                for (address in addresses) {
                    if (!address.isLoopbackAddress) {
                        val hostAddress = address.hostAddress ?: return null
                        if (hostAddress.indexOf(':') < 0) {
                            return hostAddress
                        }
                    }
                }
            }
        } catch (ignored: Exception) { }
        return null
    }

    override fun scan(port: Int): List<Pair<String, Int>> {
        val currentIpAddress = currentIpAddress() ?: return emptyList()
        val ip = currentIpAddress.split(".")
        if (ip.size != 4) {
            return emptyList()
        }

        val ips = ArrayList<Pair<String, Int>>()
        runBlocking {
            (0..255).map  {
                launch(Dispatchers.IO) {
                    val ipWithLamp = ping("${ip[0]}.${ip[1]}.${ip[2]}.${it}", port)
                    if (ipWithLamp != null) {
                        ips.add(Pair(ipWithLamp, port))
                    }
                }
            }
        }
        return ips
    }

    private fun ping(ipAddress: String, port: Int): String? {
        var datagramSocket: DatagramSocket? = null
        try {
            datagramSocket = DatagramSocket(null)
            val serverAddress = InetAddress.getByName(ipAddress)
            val buf = "GET".toByteArray()
            val requestPacket = DatagramPacket(buf, buf.size, serverAddress, port)
            datagramSocket.send(requestPacket)

            val message = ByteArray(8000)
            val responsePacket = DatagramPacket(message, message.size)
            datagramSocket.soTimeout = 500
            datagramSocket.receive(responsePacket)
            val result = String(message, 0, responsePacket.length)
            if (result.startsWith("CURR")) {
                println("CURR: $ipAddress")
                return ipAddress
            }
            return null
        } catch (e: Throwable) {
            return null
        } finally {
            datagramSocket?.close()
        }
    }
}