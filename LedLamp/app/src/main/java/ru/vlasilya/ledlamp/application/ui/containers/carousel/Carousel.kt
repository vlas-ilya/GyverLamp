package ru.vlasilya.ledlamp.application.ui.containers.carousel

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.vlasilya.ledlamp.R

@Composable
fun Carousel(
    modifier: Modifier = Modifier,
    content: LazyListScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(colorResource(R.color.top_elements_background))
    ) {
        Image(
            painter = painterResource(R.drawable.effect_list_background),
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.FillHeight,
            alpha = 0.2f
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(10.dp, 16.dp)
        ) {
            content()
        }
    }
}

@Preview
@Composable
fun CarouselPreview() {
    Carousel {
        item {
            CarouseItem(
                title = R.string.confetti_name,
                image = R.drawable.lamp,
                onClick = {}
            )
            CarouseItem(
                title = R.string.fire_name,
                image = R.drawable.lamp,
                onClick = {}
            )
            CarouseItem(
                title = R.string.white_fire_name,
                image = R.drawable.lamp,
                onClick = {}
            )
            CarouseItem(
                title = R.string.rainbow_3_name,
                image = R.drawable.lamp,
                onClick = {}
            )
        }
    }
}
