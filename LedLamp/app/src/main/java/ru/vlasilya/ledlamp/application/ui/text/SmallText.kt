package ru.vlasilya.ledlamp.application.ui.text

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object Constants {
    val TextSize = 14.sp
    val HorizontalPadding = 4.dp
    val VerticalPadding = 4.dp
}

@Composable
fun SmallText(
    res: Int, modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(res),
        fontSize = Constants.TextSize,
        fontWeight = FontWeight.Thin,
        modifier = modifier.padding(Constants.HorizontalPadding, Constants.VerticalPadding)
    )
}