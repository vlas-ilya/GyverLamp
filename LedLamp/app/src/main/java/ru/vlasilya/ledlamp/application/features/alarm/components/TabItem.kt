package ru.vlasilya.ledlamp.application.features.alarm.components

import android.content.Context
import androidx.compose.runtime.Composable
import ru.vlasilya.ledlamp.R
import ru.vlasilya.ledlamp.application.features.alarm.AlarmAndTimerViewModel
import ru.vlasilya.ledlamp.application.features.alarm.AlarmView
import ru.vlasilya.ledlamp.application.features.alarm.TimerView

typealias ComposableFun = @Composable () -> Unit

sealed class TabItem(var icon: Int, var title: Int, var screen: ComposableFun)

class AlarmTab(viewModel: AlarmAndTimerViewModel, context: Context) : TabItem(
    R.drawable.alarm_route_icon,
    R.string.alarm,
    { AlarmView(viewModel, context) }
)

class TimerTab(viewModel: AlarmAndTimerViewModel) : TabItem(
    R.drawable.timer_route_icon,
    R.string.timer,
    { TimerView(viewModel) }
)
