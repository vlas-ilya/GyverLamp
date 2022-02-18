package ru.vlasilya.ledlamp.domain.model

data class Characteristic(
    val name: Int,
    var value: Int,
    val minValue: Int,
    val maxValue: Int,
    val notWorked: Boolean = false
) {
    init {
        if (maxValue < value) {
            value = maxValue
        }
        if (minValue > value) {
            value = minValue
        }
    }
}
