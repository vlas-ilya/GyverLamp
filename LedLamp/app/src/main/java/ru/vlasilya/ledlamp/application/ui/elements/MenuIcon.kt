package ru.vlasilya.ledlamp.application.ui.elements

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun MenuIcon(res: Int, selected: Boolean) {
    Icon(
        painter = painterResource(res),
        contentDescription = null,
        modifier = Modifier
            .width(100.dp)
            .height(100.dp)
            .padding(15.dp)
    )
}
