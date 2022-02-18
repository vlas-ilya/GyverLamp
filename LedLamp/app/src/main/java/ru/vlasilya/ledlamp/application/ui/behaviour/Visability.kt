package ru.vlasilya.ledlamp.application.ui.behaviour

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
inline fun Visibility(
    modifier: Modifier = Modifier,
    visibly: Boolean = false,
    crossinline content: @Composable () -> Unit
) {
    Box(modifier) {
        if (visibly) {
            content()
        }
    }
}