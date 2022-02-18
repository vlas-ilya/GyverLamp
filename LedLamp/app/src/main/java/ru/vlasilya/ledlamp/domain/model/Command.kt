package ru.vlasilya.ledlamp.domain.model

import ru.vlasilya.ledlamp.domain.utils.effects

sealed class Command()

class Get() : Command() {
    override fun toString(): String {
        return "GET"
    }
}

class TimerGet() : Command() {
    override fun toString(): String {
        return "TMR_GET"
    }
}

class AlarmGet() : Command() {
    override fun toString(): String {
        return "ALM_GET"
    }
}

class CarouselGet() : Command() {
    override fun toString(): String {
        return "FAV_GET"
    }
}

class SwitchOn() : Command() {
    override fun toString(): String {
        return "P_ON"
    }
}
class SwitchOff() : Command() {
    override fun toString(): String {
        return "P_OFF"
    }
}

class SetEffectNumber(private val number: Int) : Command() {
    override fun toString(): String {
        return "EFF$number"
    }
}

class SetBright(private val value: Int) : Command() {
    override fun toString(): String {
        return "BRI$value"
    }
}
class SetSpeed(private val value: Int) : Command() {
    override fun toString(): String {
        return "SPD$value"
    }
}
class SetScale(private val value: Int) : Command() {
    override fun toString(): String {
        return "SCA$value"
    }
}


class SetTimer(private val state: TimerState, private val value: Int) : Command() {
    override fun toString(): String {
        return "TMR_SET ${if (state == TimerState.ON) 1 else 0} 1 $value"
    }
}

class SetAlarmDayStatus(
    private val alarmDay: AlarmDay
) : Command() {
    override fun toString(): String {
        return "ALM_SET${alarmDay.index} ${alarmDay.state}"
    }
}

class SetAlarmDayTime(
    private val alarmDay: AlarmDay
) : Command() {
    override fun toString(): String {
        return "ALM_SET${alarmDay.index} ${alarmDay.time}"
    }
}

class SetAlarmDawnTime(
    private val alarmBefore: DawnTime
) : Command() {
    override fun toString(): String {
        return "DAWN${alarmBefore.command}"
    }
}

class SetCarousel(
    private val carousel: Carousel
) : Command() {
    override fun toString(): String {
        return "FAV_SET ${carousel.state.command} ${carousel.time} ${carousel.randomInterval} ${carousel.storing.command} " + effects.keys.joinToString(separator = " ") {
            if (carousel.favoriteEffects.contains(it)) "1" else "0"
        }
    }
}