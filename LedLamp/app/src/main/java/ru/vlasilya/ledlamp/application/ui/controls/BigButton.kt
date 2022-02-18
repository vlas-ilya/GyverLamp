package ru.vlasilya.ledlamp.application.ui.controls

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.vlasilya.ledlamp.R

@Composable
fun BigButton(
    label: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = { onClick() },
        modifier = modifier.fillMaxWidth(),
    ) {
        Text(
            text = stringResource(label),
            fontSize = 20.sp,
            fontWeight = FontWeight.Light,
            modifier = modifier.padding(0.dp, 0.dp),
            textAlign = TextAlign.Center,
        )
    }
}

@Preview
@Composable
fun BigButtonPreview() {
    BigButton(
        label = R.string.save_settings,
        onClick = {}
    )
}