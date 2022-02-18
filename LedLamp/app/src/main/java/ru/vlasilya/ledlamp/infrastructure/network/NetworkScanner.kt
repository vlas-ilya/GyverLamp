package ru.vlasilya.ledlamp.infrastructure.network

interface NetworkScanner {
    fun scan(port: Int): List<Pair<String, Int>>
}