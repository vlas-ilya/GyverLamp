package ru.vlasilya.ledlamp.domain.utils

import ru.vlasilya.ledlamp.R

data class Preset<T>(
    val label: Int,
    val value: T
)

val carouselTimeOptions = mapOf(
    60 * 1 to Preset(R.string.one_minute_preset, 60 * 1),
    60 * 2 to Preset(R.string.two_minutes_preset, 60 * 2),
    60 * 3 to Preset(R.string.three_minutes_preset, 60 * 3),
    60 * 5 to Preset(R.string.five_minutes_preset, 60 * 5),
    60 * 10 to Preset(R.string.ten_minutes_preset, 60 * 10),
    60 * 15 to Preset(R.string.fifteen_minutes_preset, 60 * 15),
    60 * 20 to Preset(R.string.twenty_minutes_preset, 60 * 20),
    60 * 30 to Preset(R.string.thirty_minutes_preset, 60 * 30),
    60 * 60 to Preset(R.string.sixty_minutes_preset, 60 * 60),
)

val carouselRandomOptions = mapOf(
    60 * 0 to Preset(R.string.zero_minute_preset, 60 * 0),
    60 * 1 to Preset(R.string.one_minute_preset, 60 * 1),
    60 * 2 to Preset(R.string.two_minutes_preset, 60 * 2),
    60 * 3 to Preset(R.string.three_minutes_preset, 60 * 3),
    60 * 5 to Preset(R.string.five_minutes_preset, 60 * 5),
    60 * 10 to Preset(R.string.ten_minutes_preset, 160 * 0),
    60 * 15 to Preset(R.string.fifteen_minutes_preset, 160 * 5),
    60 * 20 to Preset(R.string.twenty_minutes_preset, 260 * 0),
    60 * 30 to Preset(R.string.thirty_minutes_preset, 360 * 0),
)