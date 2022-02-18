package ru.vlasilya.ledlamp.application.ui.text

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Header(res: Int, modifier: Modifier = Modifier) {
    Text(
        text = stringResource(res),
        fontSize = 25.sp,
        fontWeight = FontWeight.Normal,
        modifier = modifier
            .padding(10.dp, 16.dp)
    )
}
