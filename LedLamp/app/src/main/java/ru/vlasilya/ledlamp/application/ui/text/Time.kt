package ru.vlasilya.ledlamp.application.ui.text

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun Time(value: Int) {
    var hour = (value / 60).toString()
    var min = (value % 60).toString()
    while (hour.length < 2) {
        hour = "0$hour"
    }
    while (min.length < 2) {
        min = "0$min"
    }
    Text(text = "$hour : $min")
}