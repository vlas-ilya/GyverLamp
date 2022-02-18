package ru.vlasilya.ledlamp.domain.model

import ru.vlasilya.ledlamp.R

data class Alarm(
    var mon: Mon,
    var tue: Tue,
    var wed: Wed,
    var thu: Thu,
    var fri: Fri,
    var sat: Sat,
    var sun: Sun,
    var dawnTime: DawnTime
) {
    fun changes(alarm: Alarm): List<Command> {
        val commands: MutableList<Command> = ArrayList()
        if (mon.state != alarm.mon.state) commands.add(SetAlarmDayStatus(alarm.mon))
        if (tue.state != alarm.tue.state) commands.add(SetAlarmDayStatus(alarm.tue))
        if (wed.state != alarm.wed.state) commands.add(SetAlarmDayStatus(alarm.wed))
        if (thu.state != alarm.thu.state) commands.add(SetAlarmDayStatus(alarm.thu))
        if (fri.state != alarm.fri.state) commands.add(SetAlarmDayStatus(alarm.fri))
        if (sat.state != alarm.sat.state) commands.add(SetAlarmDayStatus(alarm.sat))
        if (sun.state != alarm.sun.state) commands.add(SetAlarmDayStatus(alarm.sun))
        if (mon.time != alarm.mon.time) commands.add(SetAlarmDayTime(alarm.mon))
        if (tue.time != alarm.tue.time) commands.add(SetAlarmDayTime(alarm.tue))
        if (wed.time != alarm.wed.time) commands.add(SetAlarmDayTime(alarm.wed))
        if (thu.time != alarm.thu.time) commands.add(SetAlarmDayTime(alarm.thu))
        if (fri.time != alarm.fri.time) commands.add(SetAlarmDayTime(alarm.fri))
        if (sat.time != alarm.sat.time) commands.add(SetAlarmDayTime(alarm.sat))
        if (sun.time != alarm.sun.time) commands.add(SetAlarmDayTime(alarm.sun))
        if (dawnTime.command != alarm.dawnTime.command) commands.add(SetAlarmDawnTime(alarm.dawnTime))
        return commands
    }

    override fun toString(): String {
        return "ALMS ${mon.state.value} ${tue.state.value} ${wed.state.value} ${thu.state.value} ${fri.state.value} ${sat.state.value} ${sun.state.value} ${mon.time} ${tue.time} ${wed.time} ${thu.time} ${fri.time} ${sat.time} ${sun.time} ${dawnTime.command}"
    }
}

sealed class AlarmDay(
    var title: Int,
    val index: Int,
    var state: AlarmDaySate,
    var time: Int
) {
    fun state(): Int {
        return if (state == AlarmDaySate.ON) 1 else 0
    }
}

class Mon(state: AlarmDaySate, time: Int) : AlarmDay(R.string.mon, 1, state, time)
class Tue(state: AlarmDaySate, time: Int) : AlarmDay(R.string.tue, 2, state, time)
class Wed(state: AlarmDaySate, time: Int) : AlarmDay(R.string.wed, 3, state, time)
class Thu(state: AlarmDaySate, time: Int) : AlarmDay(R.string.thu, 4, state, time)
class Fri(state: AlarmDaySate, time: Int) : AlarmDay(R.string.fri, 5, state, time)
class Sat(state: AlarmDaySate, time: Int) : AlarmDay(R.string.sat, 6, state, time)
class Sun(state: AlarmDaySate, time: Int) : AlarmDay(R.string.sun, 7, state, time)

enum class AlarmDaySate(val value: String) {
    ON("1"),
    OFF("0")
}

enum class DawnTime(val command: Int, val label: Int) {
    FIFE_MINUTES(1, R.string.down_time_fife_minutes_title),
    TEN_MINUTES(2, R.string.down_time_ten_minutes_title),
    FIFTEEN_MINUTES(3, R.string.down_time_fifteen_minutes_title),
    TWENTY_MINUTES(4, R.string.down_time_twenty_minutes_title),
    TWENTY_FIVE_MINUTES(5, R.string.down_time_twenty_five_minutes_title),
    THIRTY_MINUTES(6, R.string.down_time_thirty_minutes_title),
    FORTY_MINUTES(7, R.string.down_time_forty_minutes_title),
    FIFTY_MINUTES(8, R.string.down_time_fifty_minutes_title),
    SIXTY_MINUTES(9, R.string.down_time_sixty_minutes_title);
}

fun dawnTimeByCommand(command: Int): DawnTime {
    return when (command) {
        1 -> DawnTime.FIFE_MINUTES
        2 -> DawnTime.TEN_MINUTES
        3 -> DawnTime.FIFTEEN_MINUTES
        4 -> DawnTime.TWENTY_MINUTES
        5 -> DawnTime.TWENTY_FIVE_MINUTES
        6 -> DawnTime.THIRTY_MINUTES
        7 -> DawnTime.FORTY_MINUTES
        8 -> DawnTime.FIFTY_MINUTES
        9 -> DawnTime.SIXTY_MINUTES
        else -> DawnTime.FIFE_MINUTES
    }
}
