package ru.vlasilya.ledlamp.application.features.carousel

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import ru.vlasilya.ledlamp.R
import ru.vlasilya.ledlamp.application.features.carousel.components.Effects
import ru.vlasilya.ledlamp.application.ui.containers.page.Page
import ru.vlasilya.ledlamp.application.ui.controls.Element
import ru.vlasilya.ledlamp.application.ui.controls.MenuWithLabel
import ru.vlasilya.ledlamp.application.ui.controls.SwitchWithLabel
import ru.vlasilya.ledlamp.domain.model.CarouselStoring
import ru.vlasilya.ledlamp.domain.model.CarouselState
import ru.vlasilya.ledlamp.domain.utils.carouselRandomOptions
import ru.vlasilya.ledlamp.domain.utils.carouselTimeOptions

@Composable
fun Carousel(viewModel: CarouselViewModel) {
    val carousel = viewModel.carousel.observeAsState(initial = null).value

    Page(
        header = R.string.carousel_header,
        loading = carousel == null,
        modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 55.dp)
    ) {
        carousel!!
        val currentCarouselTimeOption = carouselTimeOptions[carousel.time] ?: carouselTimeOptions[60]!!
        val currentCarouselRandomOption = carouselRandomOptions[carousel.randomInterval] ?: carouselRandomOptions[0]!!

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 0.dp, 0.dp, 0.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(R.color.colorTertiaryVariant))
            ) {
                Image(
                    painter = painterResource(R.drawable.carousel_background),
                    contentDescription = null,
                    modifier = Modifier.matchParentSize(),
                    contentScale = ContentScale.FillHeight,
                    alpha = 0.1f
                )
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 0.dp, 10.dp, 10.dp)
                ) {
                    val (stateView, rememberView, timeView, randomIntervalView) = createRefs()
                    SwitchWithLabel(
                        label = R.string.carousel_change_effects,
                        checked = carousel.state == CarouselState.ON,
                        modifier = Modifier
                            .wrapContentHeight()
                            .constrainAs(stateView) {
                                top.linkTo(parent.top)
                            },
                        onSwitch = { viewModel.switchCarousel(if (it) CarouselState.ON else CarouselState.OFF) }
                    )
                    SwitchWithLabel(
                        label = R.string.carousel_remember_state,
                        checked = carousel.storing == CarouselStoring.ON,
                        modifier = Modifier
                            .wrapContentHeight()
                            .constrainAs(rememberView) {
                                top.linkTo(stateView.bottom)
                            },
                        onSwitch = { viewModel.switchStoring(if (it) CarouselStoring.ON else CarouselStoring.OFF) }
                    )
                    MenuWithLabel(
                        modifier = Modifier
                            .wrapContentHeight()
                            .constrainAs(timeView) {
                                top.linkTo(rememberView.bottom)
                            },
                        label = R.string.carousel_time_interval,
                        onSelect = { viewModel.setTimeInterval(carouselTimeOptions[it.id]!!) },
                        currentElement = Element(currentCarouselTimeOption.value, currentCarouselTimeOption.label),
                        elements = carouselTimeOptions.values.map { item -> Element(item.value, item.label) }
                    )
                    MenuWithLabel(
                        modifier = Modifier
                            .wrapContentHeight()
                            .constrainAs(randomIntervalView) {
                                top.linkTo(timeView.bottom)
                            },
                        label = R.string.carousel_random,
                        onSelect = { viewModel.setRandomInterval(carouselTimeOptions[it.id]!!) },
                        currentElement = Element(currentCarouselRandomOption.value, currentCarouselRandomOption.label),
                        elements = carouselRandomOptions.values.map { item -> Element(item.value, item.label) }
                    )
                }
            }
            Effects(
                modifier = Modifier.wrapContentHeight(),
                favoriteEffect = carousel.favoriteEffects,
                effects = viewModel.loadEffects(),
                onSwitch = { effect, checked -> viewModel.switchEffect(effect, checked) }
            )
        }
    }
}
