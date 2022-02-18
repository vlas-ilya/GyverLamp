package ru.vlasilya.ledlamp.application.features.effects

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.vlasilya.ledlamp.application.ui.utils.viewmodel.ComposeViewModel
import ru.vlasilya.ledlamp.domain.model.Effect
import ru.vlasilya.ledlamp.infrastructure.services.EffectsService
import ru.vlasilya.ledlamp.infrastructure.services.FavoriteEffectsService
import ru.vlasilya.ledlamp.infrastructure.services.LampService
import ru.vlasilya.ledlamp.infrastructure.services.impl.OptimisticLock
import ru.vlasilya.ledlamp.utils.Data
import ru.vlasilya.ledlamp.utils.throttleLatest

class EffectsViewModel(
    private val effectsService: EffectsService,
    private val lampService: LampService,
    private val favoriteEffectsService: FavoriteEffectsService,
    private val lock: OptimisticLock,
) : ComposeViewModel() {
    val selectedEffect: MutableLiveData<Data<Effect>> = MutableLiveData()
    val effects: MutableLiveData<List<Effect>> = MutableLiveData()

    override fun onResume() {
        viewModelScope.launch(Dispatchers.IO) {
            val effect = lampService.get { it.currentEffect() }
            withContext(Dispatchers.Main) {
                selectedEffect.value = Data(effect)
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            val list = effectsService.list()
            withContext(Dispatchers.Main) {
                effects.value = list
            }
        }
    }

    fun selectEffect(effect: Effect) {
        selectedEffect.value = Data(effect)
        throttleChangeEffect(Unit)
    }

    fun changeBright(bright: Int) {
        synchronized(lock) { lock.used = true }
        val effect = selectedEffect.value ?: return
        effect.data.bright.value = bright
        selectedEffect.value = effect.copy(data = effect.data)
        throttleChangeEffect(Unit)
    }

    fun changeSpeed(speed: Int) {
        synchronized(lock) { lock.used = true }
        val effect = selectedEffect.value ?: return
        effect.data.speed.value = speed
        selectedEffect.value = effect.copy(data = effect.data)
        throttleChangeEffect(Unit)
    }

    fun changeScale(scale: Int) {
        synchronized(lock) { lock.used = true }
        val effect = selectedEffect.value ?: return
        effect.data.scale.value = scale
        selectedEffect.value = effect.copy(data = effect.data)
        throttleChangeEffect(Unit)
    }

    private val throttleChangeEffect = throttleLatest<Unit>(scope = viewModelScope) {
        viewModelScope.launch(Dispatchers.IO) {
            val value = selectedEffect.value
            if (value != null) {
                effectsService.update(value.data)
                lampService.run { it.setEffect(value.data) }
            }
        }
    }

    fun saveCurrentEffect() {
        viewModelScope.launch(Dispatchers.IO) {
            val effect = selectedEffect.value ?: return@launch
            favoriteEffectsService.add(effect.data)
        }
    }
}
