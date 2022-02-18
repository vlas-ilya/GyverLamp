package ru.vlasilya.ledlamp.domain.model

data class Effect(
    val id: Int,
    val name: Int,
    val image: Int,
    val bright: Characteristic,
    val speed: Characteristic,
    val scale: Characteristic,
)

