package ru.vlasilya.ledlamp.application.ui.containers.carousel

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.vlasilya.ledlamp.R

@Composable
fun CarouseItem(
    title: Int,
    image: Int? = null,
    onClick: () -> Unit
) {
    val name = stringResource(title);
    Card(
        modifier = Modifier
            .width(150.dp)
            .height(150.dp)
            .clickable { onClick() },
        elevation = 10.dp
    ) {
        if (image != null) {
            Image(
                painter = painterResource(image),
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
            )
        }
        Column(
            modifier = Modifier.padding(15.dp)
        ) {
            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.W900,
                            color = colorResource(R.color.carouseItemText)
                        )
                    ) {
                        append(name)
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun CarouseItemPreview() {
    CarouseItem(
        title = R.string.confetti_name,
        image = R.drawable.lamp,
        onClick = {}
    )
}