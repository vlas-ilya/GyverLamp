package ru.vlasilya.ledlamp.application.features.effects.components

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import ru.vlasilya.ledlamp.application.ui.behaviour.Visibility
import ru.vlasilya.ledlamp.application.ui.controls.SliderWithTitle
import ru.vlasilya.ledlamp.application.ui.text.SubHeader
import ru.vlasilya.ledlamp.domain.model.Effect

@Composable
fun EffectSettingsCard(
    selectedEffect: Effect?,
    modifier: Modifier = Modifier,
    onBrightChange: (Int) -> Unit,
    onSpeedChange: (Int) -> Unit,
    onScaleChange: (Int) -> Unit,
) {
    ConstraintLayout(modifier = modifier) {
        val (header, bright, speed, scale) = createRefs()

        if (selectedEffect != null) {
            SubHeader(selectedEffect.name, Modifier.constrainAs(header) { top.linkTo(parent.top) })

            Visibility(
                visibly = !selectedEffect.bright.notWorked,
                modifier = Modifier.constrainAs(bright) { top.linkTo(header.bottom) }
            ) {
                SliderWithTitle(
                    title = selectedEffect.bright.name,
                    value = selectedEffect.bright.value,
                    minValue = selectedEffect.bright.minValue,
                    maxValue = selectedEffect.bright.maxValue,
                    onChange = { onBrightChange(it) },
                )
            }

            Visibility(
                visibly = !selectedEffect.speed.notWorked,
                modifier = Modifier.constrainAs(speed) { top.linkTo(bright.bottom) }
            ) {
                SliderWithTitle(
                    title = selectedEffect.speed.name,
                    value = selectedEffect.speed.value,
                    minValue = selectedEffect.speed.minValue,
                    maxValue = selectedEffect.speed.maxValue,
                    onChange = { onSpeedChange(it) },
                )
            }

            Visibility(
                visibly = !selectedEffect.scale.notWorked,
                modifier = Modifier.constrainAs(scale) { top.linkTo(speed.bottom) }
            ) {
                SliderWithTitle(
                    title = selectedEffect.scale.name,
                    value = selectedEffect.scale.value,
                    minValue = selectedEffect.scale.minValue,
                    maxValue = selectedEffect.scale.maxValue,
                    onChange = { onScaleChange(it) },
                )
            }
        }
    }
}
