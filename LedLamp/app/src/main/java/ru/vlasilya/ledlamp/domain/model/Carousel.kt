package ru.vlasilya.ledlamp.domain.model

import ru.vlasilya.ledlamp.domain.utils.effects

enum class CarouselState(val command: String) {
    ON("1"),
    OFF("0")
}

enum class CarouselStoring(val command: String) {
    ON("1"),
    OFF("0")
}

data class Carousel(
    var state: CarouselState,
    var storing: CarouselStoring,
    var time: Int,
    var randomInterval: Int,
    var favoriteEffects: MutableList<Int>
) {
    override fun toString(): String {
        return "FAV ${state.command} $time $randomInterval ${storing.command} " + effects.keys.joinToString(separator = " ") {
            if (favoriteEffects.contains(it)) "1" else "0"
        }
    }
}

