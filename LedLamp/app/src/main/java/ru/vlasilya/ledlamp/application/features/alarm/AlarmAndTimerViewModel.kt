package ru.vlasilya.ledlamp.application.features.alarm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.vlasilya.ledlamp.application.ui.utils.viewmodel.ComposeViewModel
import ru.vlasilya.ledlamp.domain.model.*
import ru.vlasilya.ledlamp.infrastructure.events.Disposable
import ru.vlasilya.ledlamp.infrastructure.events.EventDispatcher
import ru.vlasilya.ledlamp.infrastructure.events.LampWasUpdatedEvent
import ru.vlasilya.ledlamp.infrastructure.services.LampService
import ru.vlasilya.ledlamp.utils.debounce

class AlarmAndTimerViewModel(
    private val lampService: LampService,
    private val eventDispatcher: EventDispatcher,
) : ComposeViewModel() {
    private var disposable: Disposable? = null

    var alarm: MutableLiveData<Alarm> = MutableLiveData()
    val timer: MutableLiveData<Timer> = MutableLiveData()

    override fun onResume() {
        loadTimer()
        loadAlarm()
        viewModelScope.launch(Dispatchers.IO) {
            disposable?.dispose()
            disposable = eventDispatcher.subscribe {
                withContext(Dispatchers.IO) {
                    if (it is LampWasUpdatedEvent) {
                        val currentTimer = lampService.get { it.currentTimer() }
                        withContext(Dispatchers.Main) {
                            if (currentTimer.state == TimerState.ON) {
                                timer.value = currentTimer
                            }
                            if (currentTimer.state == TimerState.OFF && currentTimer.state != timer.value?.state) {
                                timer.value = Timer(
                                    currentTimer.state,
                                    currentTimer.lastSavedTime ?: currentTimer.time,
                                    currentTimer.lastSavedTime
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun loadTimer() {
        viewModelScope.launch(Dispatchers.IO) {
            val currentTimer = lampService.get { it.currentTimer() }
            withContext(Dispatchers.Main) {
                timer.value = Timer(
                    currentTimer.state,
                    if (currentTimer.state == TimerState.ON) currentTimer.time
                    else currentTimer.lastSavedTime ?: currentTimer.time,
                    currentTimer.lastSavedTime
                )
            }
        }
    }

    private fun loadAlarm() {
        viewModelScope.launch(Dispatchers.IO) {
            val currentAlarm = lampService.get { it.currentAlarm() }
            withContext(Dispatchers.Main) {
                alarm.value = currentAlarm
            }
        }
    }

    fun setTimer(time: Int) {
        timer.value = Timer(timer.value?.state ?: TimerState.OFF, time, time)
    }

    fun startTimer() {
        viewModelScope.launch(Dispatchers.IO) {
            lampService.run {
                it.setTimer(timer.value?.time ?: 0)
                it.switchTimerOn()
            }
            loadTimer()
        }
    }

    fun stopTimer() {
        viewModelScope.launch(Dispatchers.IO) {
            lampService.run { it.switchTimerOff() }
            loadTimer()
        }
    }

    fun switchAlarm(day: AlarmDay, value: Boolean) {
        val currentAlarm = alarm.value ?: return

        when(day) {
            is Mon -> currentAlarm.mon.state = if (value) AlarmDaySate.ON else AlarmDaySate.OFF
            is Tue -> currentAlarm.tue.state = if (value) AlarmDaySate.ON else AlarmDaySate.OFF
            is Wed -> currentAlarm.wed.state = if (value) AlarmDaySate.ON else AlarmDaySate.OFF
            is Fri -> currentAlarm.fri.state = if (value) AlarmDaySate.ON else AlarmDaySate.OFF
            is Thu -> currentAlarm.thu.state = if (value) AlarmDaySate.ON else AlarmDaySate.OFF
            is Sat -> currentAlarm.sat.state = if (value) AlarmDaySate.ON else AlarmDaySate.OFF
            is Sun -> currentAlarm.sun.state = if (value) AlarmDaySate.ON else AlarmDaySate.OFF
        }

        alarm.value = null
        alarm.value = currentAlarm.copy()
        debounceChangeEffect(Unit)
    }

    fun changeAlarm(day: AlarmDay, value: Int) {
        val currentAlarm = alarm.value ?: return

        when(day) {
            is Mon -> currentAlarm.mon.time = value
            is Tue -> currentAlarm.tue.time = value
            is Wed -> currentAlarm.wed.time = value
            is Fri -> currentAlarm.fri.time = value
            is Thu -> currentAlarm.thu.time = value
            is Sat -> currentAlarm.sat.time = value
            is Sun -> currentAlarm.sun.time = value
        }

        alarm.value = null
        alarm.value = currentAlarm.copy()
        debounceChangeEffect(Unit)
    }

    fun setDawnTime(value: DawnTime) {
        val currentAlarm = alarm.value ?: return
        currentAlarm.dawnTime = value
        alarm.value = null
        alarm.value = currentAlarm.copy()
        debounceChangeEffect(Unit)
    }

    private val debounceChangeEffect = debounce<Unit>(scope = viewModelScope) {
        withContext(Dispatchers.IO) {
            val currentAlarm = alarm.value ?: return@withContext
            lampService.run { it.setAlarm(currentAlarm) }
        }
    }
}