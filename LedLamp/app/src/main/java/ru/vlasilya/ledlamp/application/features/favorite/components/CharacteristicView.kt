package ru.vlasilya.ledlamp.application.features.favorite.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import ru.vlasilya.ledlamp.domain.model.Characteristic

@Composable
fun CharacteristicView(
    characteristic: Characteristic,
    modifier: Modifier
) {
    val maxValue = characteristic.maxValue - characteristic.minValue
    val currentValue = characteristic.value - characteristic.minValue
    ConstraintLayout(modifier = modifier.padding(0.dp, 5.dp)) {
        val (name, value) = createRefs()
        Text(
            text = stringResource(characteristic.name),
            fontWeight = FontWeight.Light,
            modifier = Modifier.constrainAs(name) { top.linkTo(parent.top) }
        )
        LinearProgressIndicator(
            progress = currentValue.toFloat() / maxValue.toFloat(),
            modifier = Modifier.fillMaxWidth().constrainAs(value) {
                top.linkTo(name.bottom)
            },
        )
    }
}