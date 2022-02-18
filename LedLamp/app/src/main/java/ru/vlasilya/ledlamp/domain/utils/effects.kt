package ru.vlasilya.ledlamp.domain.utils

import io.reactivex.functions.Function3
import ru.vlasilya.ledlamp.R
import ru.vlasilya.ledlamp.domain.model.*

fun orDefault(value: Int, default: Int) = if (value == -1) default else value

val effects = mapOf(
    0 to Function3 { bright: Int, speed: Int, scale: Int ->
        Effect(
            0,
            R.string.confetti_name,
            R.drawable.confetti,
            Characteristic(R.string.confetti_bright_name, orDefault(bright, 100), 0, 255),
            Characteristic(R.string.confetti_speed_name, orDefault(speed, 40), 0, 82),
            Characteristic(R.string.confetti_scale_name, orDefault(scale, 150), 0, 150),
        )
    },
    1 to Function3 { bright: Int, speed: Int, scale: Int ->
        Effect(
            1,
            R.string.fire_name,
            R.drawable.fire,
            Characteristic(R.string.fire_bright_name, orDefault(bright, 120), 0, 255),
            Characteristic(R.string.fire_speed_name, orDefault(speed, 20), 0, 40),
            Characteristic(R.string.fire_scale_name, orDefault(scale, 105), 0, 120),
        )
    },
    2 to Function3 { bright: Int, speed: Int, scale: Int ->
        Effect(
            2,
            R.string.white_fire_name,
            R.drawable.white_fire,
            Characteristic(R.string.white_fire_bright_name, orDefault(bright, 120), 0, 255),
            Characteristic(R.string.white_fire_speed_name, orDefault(speed, 20), 0, 40),
            Characteristic(R.string.white_fire_scale_name, orDefault(scale, 0), 0, 255),
        )
    },
    3 to Function3 { bright: Int, speed: Int, scale: Int ->
        Effect(
            3,
            R.string.rainbow_vertical_name,
            R.drawable.rainbow_vertical,
            Characteristic(R.string.rainbow_vertical_bright_name, orDefault(bright, 150), 0, 255),
            Characteristic(R.string.rainbow_vertical_speed_name, orDefault(speed, 25), 0, 70),
            Characteristic(R.string.rainbow_vertical_scale_name, orDefault(scale, 13), 5, 70),
        )
    },
    4 to Function3 { bright: Int, speed: Int, scale: Int ->
        Effect(
            4,
            R.string.rainbow_horizontal_name,
            R.drawable.rainbow_horizontal,
            Characteristic(R.string.rainbow_horizontal_bright_name, orDefault(bright, 150), 0, 255),
            Characteristic(R.string.rainbow_horizontal_speed_name, orDefault(speed, 18), 0, 70),
            Characteristic(R.string.rainbow_horizontal_scale_name, orDefault(scale, 33), 0, 70),
        )
    },
    5 to Function3 { bright: Int, speed: Int, scale: Int ->
        Effect(
            5,
            R.string.rainbow_diagonal_name,
            R.drawable.rainbow_diagonal,
            Characteristic(R.string.rainbow_diagonal_bright_name, orDefault(bright, 150), 0, 255),
            Characteristic(R.string.rainbow_diagonal_speed_name, orDefault(speed, 20), 0, 70),
            Characteristic(R.string.rainbow_diagonal_scale_name, orDefault(scale, 50), 0, 100),
        )
    },
    6 to Function3 { bright: Int, speed: Int, scale: Int ->
        Effect(
            6,
            R.string.change_of_colour_name,
            R.drawable.change_of_colour,
            Characteristic(R.string.change_of_colour_bright_name, orDefault(bright, 40), 0, 255),
            Characteristic(R.string.change_of_colour_speed_name, orDefault(speed, 0), 0, 255, true),
            Characteristic(R.string.change_of_colour_scale_name, orDefault(scale, 20), 0, 100),
        )
    },
    7 to Function3 { bright: Int, speed: Int, scale: Int ->
        Effect(
            7,
            R.string.madness_3_name,
            R.drawable.madness_3,
            Characteristic(R.string.madness_3_bright_name, orDefault(bright, 100), 0, 255),
            Characteristic(R.string.madness_3_speed_name, orDefault(speed, 70), 0, 200),
            Characteristic(R.string.madness_3_scale_name, orDefault(scale, 100), 0, 255),
        )
    },
    8 to Function3 { bright: Int, speed: Int, scale: Int ->
        Effect(
            8,
            R.string.clouds_3_name,
            R.drawable.clouds_3,
            Characteristic(R.string.clouds_3_bright_name, orDefault(bright, 100), 0, 255),
            Characteristic(R.string.clouds_3_speed_name, orDefault(speed, 10), 0, 100),
            Characteristic(R.string.clouds_3_scale_name, orDefault(scale, 30), 20, 100),
        )
    },
    9 to Function3 { bright: Int, speed: Int, scale: Int ->
        Effect(
            9,
            R.string.lava_3_name,
            R.drawable.lava_3,
            Characteristic(R.string.lava_3_bright_name, orDefault(bright, 100), 0, 255),
            Characteristic(R.string.lava_3_speed_name, orDefault(speed, 6), 1, 100),
            Characteristic(R.string.lava_3_scale_name, orDefault(scale, 30), 0, 100),
        )
    },
    10 to Function3 { bright: Int, speed: Int, scale: Int ->
        Effect(
            10,
            R.string.plasma_3_name,
            R.drawable.plasma_3,
            Characteristic(R.string.plasma_3_bright_name, orDefault(bright, 100), 0, 255),
            Characteristic(R.string.plasma_3_speed_name, orDefault(speed, 20), 1, 100),
            Characteristic(R.string.plasma_3_scale_name, orDefault(scale, 20), 10, 240),
        )
    },
    11 to Function3 { bright: Int, speed: Int, scale: Int ->
        Effect(
            11,
            R.string.rainbow_3_name,
            R.drawable.rainbow_3,
            Characteristic(R.string.rainbow_3_bright_name, orDefault(bright, 100), 0, 255),
            Characteristic(R.string.rainbow_3_speed_name, orDefault(speed, 10), 1, 100),
            Characteristic(R.string.rainbow_3_scale_name, orDefault(scale, 60), 20, 230),
        )
    },
    12 to Function3 { bright: Int, speed: Int, scale: Int ->
        Effect(
            12,
            R.string.peacock_3_name,
            R.drawable.peacock_3,
            Characteristic(R.string.peacock_3_bright_name, orDefault(bright, 100), 0, 255),
            Characteristic(R.string.peacock_3_speed_name, orDefault(speed, 10), 1, 100),
            Characteristic(R.string.peacock_3_scale_name, orDefault(scale, 60), 20, 230),
        )
    },
    13 to Function3 { bright: Int, speed: Int, scale: Int ->
        Effect(
            13,
            R.string.zebra_3_name,
            R.drawable.zebra_3,
            Characteristic(R.string.zebra_3_bright_name, orDefault(bright, 100), 0, 255),
            Characteristic(R.string.zebra_3_speed_name, orDefault(speed, 20), 1, 100),
            Characteristic(R.string.zebra_3_scale_name, orDefault(scale, 30), 10, 100),
        )
    },
    14 to Function3 { bright: Int, speed: Int, scale: Int ->
        Effect(
            14,
            R.string.forest_3_name,
            R.drawable.forest_3,
            Characteristic(R.string.forest_3_bright_name, orDefault(bright, 100), 0, 255),
            Characteristic(R.string.forest_3_speed_name, orDefault(speed, 5), 1, 100),
            Characteristic(R.string.forest_3_scale_name, orDefault(scale, 30), 10, 100),
        )
    },
    15 to Function3 { bright: Int, speed: Int, scale: Int ->
        Effect(
            15,
            R.string.ocean_3_name,
            R.drawable.ocean_3,
            Characteristic(R.string.ocean_3_bright_name, orDefault(bright, 200), 0, 255),
            Characteristic(R.string.ocean_3_speed_name, orDefault(speed, 8), 2, 100),
            Characteristic(R.string.ocean_3_scale_name, orDefault(scale, 35), 20, 100),
        )
    },
    16 to Function3 { bright: Int, speed: Int, scale: Int ->
        Effect(
            16,
            R.string.colour_name,
            R.drawable.colour,
            Characteristic(R.string.colour_bright_name, orDefault(bright, 100), 0, 255),
            Characteristic(R.string.colour_speed_name, orDefault(speed, 0), 0, 255, true),
            Characteristic(R.string.colour_scale_name, orDefault(scale, 35), 0, 120),
        )
    },
    17 to Function3 { bright: Int, speed: Int, scale: Int ->
        Effect(
            17,
            R.string.snowfall_name,
            R.drawable.snowfall,
            Characteristic(R.string.snowfall_bright_name, orDefault(bright, 100), 0, 255),
            Characteristic(R.string.snowfall_speed_name, orDefault(speed, 20), 0, 50),
            Characteristic(R.string.snowfall_scale_name, orDefault(scale, 60), 0, 100),
        )
    },
    18 to Function3 { bright: Int, speed: Int, scale: Int ->
        Effect(
            18,
            R.string.blizzard_name,
            R.drawable.blizzard,
            Characteristic(R.string.blizzard_bright_name, orDefault(bright, 110), 0, 255),
            Characteristic(R.string.blizzard_speed_name, orDefault(speed, 20), 10, 50),
            Characteristic(R.string.blizzard_scale_name, orDefault(scale, 20), 0, 100),
        )
    },
    19 to Function3 { bright: Int, speed: Int, scale: Int ->
        Effect(
            19,
            R.string.starfall_name,
            R.drawable.starfall,
            Characteristic(R.string.starfall_bright_name, orDefault(bright, 110), 0, 255),
            Characteristic(R.string.starfall_speed_name, orDefault(speed, 20), 10, 50),
            Characteristic(R.string.starfall_scale_name, orDefault(scale, 20), 0, 100),
        )
    },
    20 to Function3 { bright: Int, speed: Int, scale: Int ->
        Effect(
            20,
            R.string.matrix_name,
            R.drawable.matrix,
            Characteristic(R.string.matrix_bright_name, orDefault(bright, 100), 0, 255),
            Characteristic(R.string.matrix_speed_name, orDefault(speed, 70), 0, 100),
            Characteristic(R.string.matrix_scale_name, orDefault(scale, 40), 0, 100),
        )
    },
    21 to Function3 { bright: Int, speed: Int, scale: Int ->
        Effect(
            21,
            R.string.fireflies_name,
            R.drawable.fireflies,
            Characteristic(R.string.fireflies_bright_name, orDefault(bright, 100), 0, 255),
            Characteristic(R.string.fireflies_speed_name, orDefault(speed, 60), 0, 100),
            Characteristic(R.string.fireflies_scale_name, orDefault(scale, 30), 0, 100),
        )
    },
    22 to Function3 { bright: Int, speed: Int, scale: Int ->
        Effect(
            22,
            R.string.fireflies_with_loop_name,
            R.drawable.fireflies_with_loop,
            Characteristic(R.string.fireflies_with_loop_bright_name, orDefault(bright, 110), 0, 255),
            Characteristic(R.string.fireflies_with_loop_speed_name, orDefault(speed, 30), 0, 100),
            Characteristic(R.string.fireflies_with_loop_scale_name, orDefault(scale, 50), 0, 255),
        )
    },
    23 to Function3 { bright: Int, speed: Int, scale: Int ->
        Effect(
            23,
            R.string.paintball_name,
            R.drawable.paintball,
            Characteristic(R.string.paintball_bright_name, orDefault(bright, 110), 0, 255),
            Characteristic(R.string.paintball_speed_name, orDefault(speed, 30), 0, 100),
            Characteristic(R.string.paintball_scale_name, orDefault(scale, 180), 0, 255),
        )
    },
    24 to Function3 { bright: Int, speed: Int, scale: Int ->
        Effect(
            24,
            R.string.wandering_cube_name,
            R.drawable.wandering_cube,
            Characteristic(R.string.wandering_cube_bright_name, orDefault(bright, 110), 0, 255),
            Characteristic(R.string.wandering_cube_speed_name, orDefault(speed, 70), 0, 150),
            Characteristic(R.string.wandering_cube_scale_name, orDefault(scale, 10), 0, 255),
        )
    },
    25 to Function3 { bright: Int, speed: Int, scale: Int ->
        Effect(
            25,
            R.string.white_light_name,
            R.drawable.white_light,
            Characteristic(R.string.white_light_bright_name, orDefault(bright, 100), 0, 255),
            Characteristic(R.string.white_light_speed_name, orDefault(speed, 200), 0, 255),
            Characteristic(R.string.white_light_scale_name, orDefault(scale, 0), 0, 255),
        )
    },
)

