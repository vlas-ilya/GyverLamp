package ru.vlasilya.ledlamp.infrastructure.services

interface LampPullService {
    fun startPulling()
    fun stopPulling()
}