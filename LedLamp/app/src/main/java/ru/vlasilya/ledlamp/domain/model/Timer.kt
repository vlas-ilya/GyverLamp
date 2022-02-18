package ru.vlasilya.ledlamp.domain.model

enum class TimerState(val command: String) {
    ON("1"),
    OFF("0")
}

data class Timer(
    var state: TimerState,
    var time: Int,
    var lastSavedTime: Int?
) {
    override fun toString(): String {
        return "TMR ${state.command} 1 $time"
    }
}
