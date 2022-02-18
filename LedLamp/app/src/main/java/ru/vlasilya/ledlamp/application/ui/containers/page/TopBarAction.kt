package ru.vlasilya.ledlamp.application.ui.containers.page

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.vlasilya.ledlamp.R

@Composable
fun TopBarAction(
    icon: Int,
    onClick: () -> Unit
) {
    IconButton(onClick = { onClick() }) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier
                .width(50.dp)
                .height(50.dp)
                .padding(15.dp)
        )
    }
}

@Preview
@Composable
fun TopBarActionPreview() {
    TopBarAction(
        icon = R.drawable.favorite_route_icon,
        onClick = {}
    )
}