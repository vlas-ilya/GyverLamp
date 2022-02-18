package ru.vlasilya.ledlamp.application.features

import ru.vlasilya.ledlamp.R

data class Route(
    val id: String,
    val label: Int,
    val icon: Int,
);

object Routes {
    val effects = Route("effects", R.string.effects_route_label, R.drawable.effects_route_icon)
    val favorite = Route("favorite", R.string.favorite_route_label, R.drawable.favorite_route_icon)
    val carousel = Route("carousel", R.string.carousel_route_label, R.drawable.carousel_route_icon)
    val alarm = Route("alarm", R.string.alarm_route_label, R.drawable.alarm_route_icon)
    val settings = Route("settings", R.string.settings_route_label, R.drawable.settings_route_icon)
}
