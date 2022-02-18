package ru.vlasilya.ledlamp.application.features.carousel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.vlasilya.ledlamp.application.ui.utils.viewmodel.ComposeViewModel
import ru.vlasilya.ledlamp.domain.model.Effect
import ru.vlasilya.ledlamp.domain.model.Carousel
import ru.vlasilya.ledlamp.domain.model.CarouselState
import ru.vlasilya.ledlamp.domain.model.CarouselStoring
import ru.vlasilya.ledlamp.domain.utils.Preset
import ru.vlasilya.ledlamp.domain.utils.effects
import ru.vlasilya.ledlamp.infrastructure.services.LampService

class CarouselViewModel(
    private val lampService: LampService
) : ComposeViewModel() {
    val carousel: MutableLiveData<Carousel> = MutableLiveData()

    override fun onResume() {
        super.onResume()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val lampCarousel = lampService.get { it.carousel() }
                withContext(Dispatchers.Main) {
                    carousel.value = lampCarousel
                }
            }
        }
    }

    fun switchCarousel(value: CarouselState) {
        viewModelScope.launch(Dispatchers.IO) {
            lampService.run {  it.switchCarouselState(value) }
            onResume()
        }
    }

    fun switchStoring(value: CarouselStoring) {
        viewModelScope.launch(Dispatchers.IO) {
            lampService.run { it.switchCarouselStoring(value) }
            onResume()
        }
    }

    fun setTimeInterval(preset: Preset<Int>) {
        viewModelScope.launch(Dispatchers.IO) {
            lampService.run { it.setCarouselTimeInterval(preset.value) }
            onResume()
        }
    }

    fun setRandomInterval(preset: Preset<Int>) {
        viewModelScope.launch(Dispatchers.IO) {
            lampService.run { it.setCarouselRandomInterval(preset.value) }
            onResume()
        }
    }

    fun switchEffect(effect: Effect, included: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            lampService.run { it.switchCarouselEffect(effect, included) }
            onResume()
        }
    }

    fun loadEffects(): List<Effect> {
        return effects.values.map { it.apply(0, 0, 0) }
    }
}