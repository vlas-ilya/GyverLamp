package ru.vlasilya.ledlamp.application.features.effects

import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import ru.vlasilya.ledlamp.R
import ru.vlasilya.ledlamp.application.features.effects.components.EffectSettingsCard
import ru.vlasilya.ledlamp.application.ui.containers.carousel.CarouseItem
import ru.vlasilya.ledlamp.application.ui.containers.carousel.Carousel
import ru.vlasilya.ledlamp.application.ui.containers.page.Page
import ru.vlasilya.ledlamp.application.ui.containers.page.TopBarAction
import ru.vlasilya.ledlamp.domain.model.Effect

@Composable
fun Effects(
    viewModel: EffectsViewModel,
    onOpenSettings: () -> Unit,
) {
    val effects = viewModel.effects.observeAsState(initial = emptyList())
    val selectedEffect = viewModel.selectedEffect.observeAsState(initial = null).value

    EffectsView(
        selectedEffect = selectedEffect?.data,
        effects = effects.value,
        onSelectEffect = { viewModel.selectEffect(it) },
        onSaveCurrentEffect = { viewModel.saveCurrentEffect() },
        onChangeBright = { viewModel.changeBright(it) },
        onChangeSpeed = { viewModel.changeSpeed(it) },
        onChangeScale = { viewModel.changeScale(it) },
        onOpenSettings = onOpenSettings
    )
}

@Composable
fun EffectsView(
    selectedEffect: Effect?,
    effects: List<Effect>,
    onSelectEffect: (Effect) -> Unit,
    onSaveCurrentEffect: () -> Unit,
    onChangeBright: (Int) -> Unit,
    onChangeSpeed: (Int) -> Unit,
    onChangeScale: (Int) -> Unit,
    onOpenSettings: () -> Unit,
) {
    Page(
        header = R.string.effects_header,
        loading = effects.isEmpty(),
        actions = listOf({
            TopBarAction(
                icon = R.drawable.favorite_route_icon,
                onClick = { onSaveCurrentEffect() }
            )
        }, {
            TopBarAction(
                icon = R.drawable.settings_route_icon,
                onClick = { onOpenSettings() }
            )
        })
    ) {
        val (list, settings) = createRefs()

        Carousel(modifier = Modifier.constrainAs(list) { top.linkTo(parent.top) }) {
            itemsIndexed(effects) { _, effect ->
                CarouseItem(effect.name, image = effect.image) {
                    onSelectEffect(effect)
                }
            }
        }

        EffectSettingsCard(
            selectedEffect = selectedEffect,
            modifier = Modifier.constrainAs(settings) { top.linkTo(list.bottom) },
            onBrightChange = { onChangeBright(it) },
            onSpeedChange = { onChangeSpeed(it) },
            onScaleChange = { onChangeScale(it) },
        )
    }
}
