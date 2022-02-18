package ru.vlasilya.ledlamp.domain.utils

import ru.vlasilya.ledlamp.domain.model.*
import ru.vlasilya.ledlamp.infrastructure.network.LampInfo

fun makeAlarmFromString(value: String): Alarm? {
    return try {
        val items = value.split(" ")
        Alarm(
            Mon(if (items[1] == "1") AlarmDaySate.ON else AlarmDaySate.OFF, items[8].toInt()),
            Tue(if (items[2] == "1") AlarmDaySate.ON else AlarmDaySate.OFF, items[9].toInt()),
            Wed(if (items[3] == "1") AlarmDaySate.ON else AlarmDaySate.OFF, items[10].toInt()),
            Thu(if (items[4] == "1") AlarmDaySate.ON else AlarmDaySate.OFF, items[11].toInt()),
            Fri(if (items[5] == "1") AlarmDaySate.ON else AlarmDaySate.OFF, items[12].toInt()),
            Sat(if (items[6] == "1") AlarmDaySate.ON else AlarmDaySate.OFF, items[13].toInt()),
            Sun(if (items[7] == "1") AlarmDaySate.ON else AlarmDaySate.OFF, items[14].toInt()),
            dawnTimeByCommand(items[15].toInt())
        )
    } catch (e: Throwable) {
        e.printStackTrace()
        return null
    }
}

fun makeTimerFromString(value: String, lampTimerLastSavedTime: Int?): Timer? {
    return try {
        val items = value.split(" ")
        Timer(
            if (items[1] == "1") TimerState.ON else TimerState.OFF,
            items[3].toInt(),
            lampTimerLastSavedTime
        )
    } catch (e: Throwable) {
        e.printStackTrace()
        null
    }
}

fun makeEffectFromString(value: String): Effect? {
    return try {
        val items = value.split(" ")
        val make = effects[items[1].toInt()] ?: return null
        make.apply(items[2].toInt(), items[3].toInt(), items[4].toInt())
    } catch (e: Throwable) {
        e.printStackTrace()
        return null
    }
}

fun makeCarouselFromString(value: String): Carousel? {
    return try {
        val items = value.split(" ")

        val favoriteEffects: MutableList<Int> = ArrayList();
        favoriteEffects.addAll(effects.keys.map {
            val index = it + 5;
            if (items.size > index && items[index] == "1") it else -1
        }.filter { it != -1 })

        Carousel(
            if (items[1] == "1") CarouselState.ON else CarouselState.OFF,
            if (items[4] == "1") CarouselStoring.ON else CarouselStoring.OFF,
            items[2].toInt(),
            items[3].toInt(),
            favoriteEffects
        )
    } catch (e: Throwable) {
        e.printStackTrace()
        return null
    }
}

fun makeLampStateFromString(value: String): LampState? {
    return try {
        val items = value.split(" ")
        if (items[5] == "1") LampState.ON else LampState.OFF
    } catch (e: Throwable) {
        e.printStackTrace()
        return null
    }
}

fun makeLampFromLampInfo(
    info: LampInfo,
    lampTimerLastSavedTime: Int? = null,
    isOnline: Boolean = false
): Lamp? {
    return try {
        val state = makeLampStateFromString(info.baseInfo) ?: return null
        val effect = makeEffectFromString(info.baseInfo) ?: return null
        val timer = makeTimerFromString(info.timerInfo, lampTimerLastSavedTime) ?: return null
        val alarm = makeAlarmFromString(info.alarmInfo) ?: return null
        val carousel = makeCarouselFromString(info.carouselInfo) ?: return null
        Lamp(state, effect, timer, alarm, carousel, isOnline)
    } catch (e: Throwable) {
        e.printStackTrace()
        return null
    }
}
