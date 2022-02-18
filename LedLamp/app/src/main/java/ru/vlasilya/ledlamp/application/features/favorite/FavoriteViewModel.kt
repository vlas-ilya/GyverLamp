package ru.vlasilya.ledlamp.application.features.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.vlasilya.ledlamp.application.ui.utils.viewmodel.ComposeViewModel
import ru.vlasilya.ledlamp.domain.model.Effect
import ru.vlasilya.ledlamp.domain.model.FavoriteEffect
import ru.vlasilya.ledlamp.infrastructure.services.FavoriteEffectsService
import ru.vlasilya.ledlamp.infrastructure.services.LampService

class FavoriteViewModel(
    private val favoriteEffectsService: FavoriteEffectsService,
    private val lampService: LampService
) : ComposeViewModel() {
    val effects: MutableLiveData<List<FavoriteEffect>> = MutableLiveData()

    override fun onResume() {
        viewModelScope.launch(Dispatchers.IO) {
            val actualEffects = favoriteEffectsService.list()
            withContext(Dispatchers.Main) {
                effects.value = actualEffects
            }
        }
    }

    fun delete(effect: FavoriteEffect) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteEffectsService.delete(effect.id)
            val actualEffects = favoriteEffectsService.list()
            withContext(Dispatchers.Main) {
                effects.value = actualEffects
            }
        }
    }

    fun setEffect(effect: FavoriteEffect) {
        viewModelScope.launch(Dispatchers.IO) {
            lampService.run { it.setEffect(effect.effect) }
        }
    }
}