package ru.vlasilya.ledlamp.application.features

import androidx.lifecycle.*
import kotlinx.coroutines.*
import ru.vlasilya.ledlamp.domain.model.LampState
import ru.vlasilya.ledlamp.infrastructure.events.Disposable
import ru.vlasilya.ledlamp.infrastructure.events.EventDispatcher
import ru.vlasilya.ledlamp.infrastructure.events.LampWasUpdatedEvent
import ru.vlasilya.ledlamp.infrastructure.services.LampPullService
import ru.vlasilya.ledlamp.infrastructure.services.LampService

class MainViewModel(
    private val lampService: LampService,
    private val pullService: LampPullService,
    private val eventDispatcher: EventDispatcher
) : ViewModel(), DefaultLifecycleObserver {
    private lateinit var disposable: Disposable


    val lampState: MutableLiveData<LampState> = MutableLiveData<LampState>()
    val isOnline: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        viewModelScope.launch(Dispatchers.IO) {
            pullService.startPulling()
        }
        disposable = eventDispatcher.subscribe { event ->
            viewModelScope.launch(Dispatchers.IO) {
                if (event is LampWasUpdatedEvent) {
                    val lamp = lampService.get { it }
                    withContext(Dispatchers.Main) {
                        lampState.value = lamp.state()
                        isOnline.value = lamp.isOnline()
                    }
                }
            }
        }
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                pullService.stopPulling()
            }
            disposable.dispose()
        }
    }

    fun toggleLamp() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if (lampService.get { it.state() } == LampState.ON) {
                    lampService.run { it.switchOff() }
                } else {
                    lampService.run { it.switchOn() }
                }
                val state = lampService.get { it.state() }
                withContext(Dispatchers.Main) {
                    lampState.value = state
                }
            }
        }
    }
}
