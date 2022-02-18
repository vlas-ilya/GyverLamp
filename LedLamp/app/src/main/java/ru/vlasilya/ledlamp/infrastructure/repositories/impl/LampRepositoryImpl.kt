package ru.vlasilya.ledlamp.infrastructure.repositories.impl

import android.content.SharedPreferences
import ru.vlasilya.ledlamp.domain.model.*
import ru.vlasilya.ledlamp.domain.utils.effects
import ru.vlasilya.ledlamp.domain.utils.makeLampFromLampInfo
import ru.vlasilya.ledlamp.infrastructure.network.LampInfo
import ru.vlasilya.ledlamp.infrastructure.repositories.LampRepository

class LampRepositoryImpl(
    private val storage: SharedPreferences
) : LampRepository {
    private val constants = object {
        val LAMP_BASE_INFO = "LAMP_BASE_INFO"
        val LAMP_IS_ONLINE = "LAMP_IS_ONLINE"
        val LAMP_TIMER_INFO = "LAMP_TIMER_INFO"
        val LAMP_TIMER_INFO_LAST_SAVED_TIME = "LAMP_TIMER_INFO_LAST_SAVED_TIME"
        val LAMP_ALARM_INFO = "LAMP_ALARM_INFO"
        val LAMP_FAVORITE_INFO = "LAMP_FAVORITE_INFO"
    }

    private fun makeDefaultLamp(): Lamp {
        return Lamp(
            state = LampState.OFF,
            currentEffect = effects[0]!!.apply(0, 0, 0),
            timer = Timer(
                state = TimerState.OFF,
                time = 0,
                lastSavedTime = 0
            ),
            alarm = Alarm(
                Mon(AlarmDaySate.OFF, 0),
                Tue(AlarmDaySate.OFF, 0),
                Wed(AlarmDaySate.OFF, 0),
                Thu(AlarmDaySate.OFF, 0),
                Fri(AlarmDaySate.OFF, 0),
                Sat(AlarmDaySate.OFF, 0),
                Sun(AlarmDaySate.OFF, 0),
                DawnTime.FIFE_MINUTES
            ),
            carousel = Carousel(
                state = CarouselState.OFF,
                storing = CarouselStoring.OFF,
                time = 60,
                randomInterval = 0,
                favoriteEffects = ArrayList()
            ),
            false
        )
    }

    override fun load(): Lamp {
        val lampTimerLastSavedTime = storage.getInt(constants.LAMP_TIMER_INFO_LAST_SAVED_TIME, -1)

        val lampBaseInfo = storage.getString(constants.LAMP_BASE_INFO, "") ?: return makeDefaultLamp()
        val lampTimerInfo = storage.getString(constants.LAMP_TIMER_INFO, "") ?: return makeDefaultLamp()
        val lampAlarmInfo = storage.getString(constants.LAMP_ALARM_INFO, "") ?: return makeDefaultLamp()
        val lampFavoriteInfo = storage.getString(constants.LAMP_FAVORITE_INFO, "") ?: return makeDefaultLamp()
        val lampIsOnline = storage.getBoolean(constants.LAMP_IS_ONLINE, false)

        return makeLampFromLampInfo(
            LampInfo(lampBaseInfo, lampTimerInfo, lampAlarmInfo, lampFavoriteInfo),
            if (lampTimerLastSavedTime == -1) null else lampTimerLastSavedTime,
            lampIsOnline
        ) ?: makeDefaultLamp()
    }

    override fun save(lamp: Lamp) {
        val (baseInfo, timerInfo, alarmInfo, favoriteInfo) = lamp.transformToLampInfo()
        val edit = storage.edit()
            .putString(constants.LAMP_BASE_INFO, baseInfo)
            .putString(constants.LAMP_TIMER_INFO, timerInfo)
            .putString(constants.LAMP_ALARM_INFO, alarmInfo)
            .putString(constants.LAMP_FAVORITE_INFO, favoriteInfo)
            .putBoolean(constants.LAMP_IS_ONLINE, lamp.isOnline())

        val lastSavedTime = lamp.currentTimer().lastSavedTime
        if (lastSavedTime != null) {
            edit.putInt(constants.LAMP_TIMER_INFO_LAST_SAVED_TIME, lastSavedTime)
        }

        edit.apply()
    }
}
