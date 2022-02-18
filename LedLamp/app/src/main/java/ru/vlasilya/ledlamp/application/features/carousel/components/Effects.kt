package ru.vlasilya.ledlamp.application.features.carousel.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.vlasilya.ledlamp.application.ui.controls.SwitchWithLabel
import ru.vlasilya.ledlamp.domain.model.Effect

@Composable
fun Effects(
    favoriteEffect: List<Int>,
    effects: List<Effect>,
    modifier: Modifier = Modifier,
    onSwitch: (Effect, Boolean) -> Unit
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState()).padding(10.dp, 6.dp, 10.dp, 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        effects.map {
            SwitchWithLabel(
                label = it.name,
                checked = favoriteEffect.contains(it.id),
                onSwitch = { checked -> onSwitch(it, checked) }
            )
        }
    }
}