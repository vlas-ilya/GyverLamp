package ru.vlasilya.ledlamp.application.ui.controls

import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.vlasilya.ledlamp.application.ui.text.Time

@Composable
fun SmallTimePicker(
    value: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Button(onClick = { onClick() }, modifier = modifier) {
        Time(value)
    }
}