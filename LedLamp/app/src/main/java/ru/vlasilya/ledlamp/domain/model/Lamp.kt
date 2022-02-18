package ru.vlasilya.ledlamp.domain.model

import ru.vlasilya.ledlamp.domain.utils.effects
import ru.vlasilya.ledlamp.infrastructure.network.LampInfo

class Lamp(
    private var state: LampState,
    private var currentEffect: Effect,
    private var timer: Timer,
    private var alarm: Alarm,
    private var carousel: Carousel,
    private var isOnline: Boolean
) {
    private val commands = ArrayList<Command>()

    fun currentEffect(): Effect {
        return currentEffect
    }

    fun state(): LampState {
        return state
    }

    fun switchOn() {
        if (state == LampState.OFF) {
            commands.add(SwitchOn())
            state = LampState.ON
        }
    }

    fun switchOff() {
        if (state == LampState.ON) {
            commands.add(SwitchOff())
            state = LampState.OFF
        }
    }

    fun setEffect(newEffect: Effect) {
        if (newEffect.id != currentEffect.id) {
            commands.add(SetEffectNumber(newEffect.id))
        }
        if (newEffect.bright.value != currentEffect.bright.value) {
            commands.add(SetBright(newEffect.bright.value))
        }
        if (newEffect.speed.value != currentEffect.speed.value) {
            commands.add(SetSpeed(newEffect.speed.value))
        }
        if (newEffect.scale.value != currentEffect.scale.value) {
            commands.add(SetScale(newEffect.scale.value))
        }
        currentEffect = newEffect
    }

    fun currentTimer(): Timer {
        return timer
    }

    fun setTimer(time: Int) {
        if (timer.time != time) {
            timer.time = time
            timer.lastSavedTime = time
            commands.add(SetTimer(timer.state, time))
        }
    }

    fun switchTimerOn() {
        if (timer.state != TimerState.ON) {
            timer.state = TimerState.ON
            commands.add(SetTimer(TimerState.ON, timer.time))
        }
    }

    fun switchTimerOff() {
        if (timer.state != TimerState.OFF) {
            timer.state = TimerState.OFF
            commands.add(SetTimer(TimerState.OFF, timer.time))
        }
    }

    fun setAlarm(alarm: Alarm) {
        val changes = this.alarm.changes(alarm)
        this.alarm = alarm
        commands.addAll(changes)
    }

    fun currentAlarm(): Alarm {
        return alarm
    }

    fun carousel(): Carousel {
        return carousel
    }

    fun pullCommands(): List<Command> {
        val commands = ArrayList<Command>(this.commands)
        this.commands.clear()
        return commands
    }

    fun transformToLampInfo(): LampInfo {
        return LampInfo(
            this.toString(),
            timer.toString(),
            alarm.toString(),
            carousel.toString()
        )
    }

    fun switchCarouselState(state: CarouselState) {
        if (carousel.state != state) {
            carousel.state = state
            commands.add(SetCarousel(carousel))
        }
    }

    fun switchCarouselStoring(storing: CarouselStoring) {
        if (carousel.storing != storing) {
            carousel.storing = storing
            commands.add(SetCarousel(carousel))
        }
    }

    fun setCarouselTimeInterval(time: Int) {
        if (carousel.time != time) {
            carousel.time = time
            commands.add(SetCarousel(carousel))
        }
    }

    fun setCarouselRandomInterval(random: Int) {
        if (carousel.randomInterval != random) {
            carousel.randomInterval = random
            commands.add(SetCarousel(carousel))
        }
    }

    fun switchCarouselEffect(effect: Effect, included: Boolean) {
        val list: List<Int> = effects.keys.map {
            if (it == effect.id) {
                if (included) it else -1
            } else {
                if (carousel.favoriteEffects.contains(it)) it else -1
            }
        }.filter { it != -1 }
        carousel.favoriteEffects.clear()
        carousel.favoriteEffects.addAll(list)
        commands.add(SetCarousel(carousel))
    }

    override fun toString(): String {
        return "CURR ${currentEffect.id} ${currentEffect.bright.value} ${currentEffect.speed.value} ${currentEffect.scale.value} ${state.command}"
    }

    fun setIsOnline(isOnline: Boolean) {
        this.isOnline = isOnline
    }

    fun isOnline(): Boolean {
        return isOnline
    }
}
