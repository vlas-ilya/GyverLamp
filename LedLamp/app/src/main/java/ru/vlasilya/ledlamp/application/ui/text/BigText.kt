package ru.vlasilya.ledlamp.application.ui.text

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BigText(
    text: String, modifier: Modifier = Modifier
) {
    Text(
        text = text,
        fontSize = 130.sp,
        fontWeight = FontWeight.Thin,
        modifier = modifier.padding(6.dp, 6.dp)
    )
}

@Preview
@Composable
fun BigTextPreview() {
    BigText("01")
}