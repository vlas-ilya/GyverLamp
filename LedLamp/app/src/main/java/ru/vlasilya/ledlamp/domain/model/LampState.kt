package ru.vlasilya.ledlamp.domain.model

enum class LampState(val command: String) {
    ON("1"), OFF("0")
}
