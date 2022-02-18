package ru.vlasilya.ledlamp.application.features.alarm

import android.app.TimePickerDialog
import android.content.Context
import android.icu.util.Calendar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import ru.vlasilya.ledlamp.R
import ru.vlasilya.ledlamp.application.features.alarm.components.AlarmItem
import ru.vlasilya.ledlamp.application.ui.controls.Element
import ru.vlasilya.ledlamp.application.ui.controls.MenuWithLabel
import ru.vlasilya.ledlamp.domain.model.DawnTime
import ru.vlasilya.ledlamp.domain.model.dawnTimeByCommand

@Composable
fun AlarmView(viewModel: AlarmAndTimerViewModel, context: Context) {
    val alarm = viewModel.alarm.observeAsState(initial = null)

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(10.dp, 10.dp, 10.dp, 60.dp)
    ) {
        val (alarms, dawn) = createRefs()
        val value = alarm.value
        if (value != null) {
            Column(
                modifier = Modifier.fillMaxWidth().constrainAs(alarms) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            ) {
                listOf(value.mon, value.tue, value.wed, value.thu, value.fri, value.sat, value.sun).map {
                    AlarmItem(
                        alarmDay = it,
                        switchAlarm = { value ->
                            viewModel.switchAlarm(it, value)
                        },
                        changeAlarm = {
                            getTimePicker(context) { time ->
                                viewModel.changeAlarm(it, time)
                            }.show()
                        }
                    )
                }
            }
            MenuWithLabel(
                label = R.string.dawn_time,
                currentElement = Element(value.dawnTime.command, value.dawnTime.label),
                elements = DawnTime.values().toList().map { Element(it.command, it.label) },
                onSelect = { viewModel.setDawnTime(dawnTimeByCommand(it.id)) },
                modifier = Modifier.fillMaxWidth().constrainAs(dawn) {
                    top.linkTo(alarms.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            )
        }
    }
}

fun getTimePicker(context: Context, onChange: (Int) -> Unit): TimePickerDialog {
    val calendar = Calendar.getInstance()
    val hour = calendar[Calendar.HOUR_OF_DAY]
    val minute = calendar[Calendar.MINUTE]

    return TimePickerDialog(
        context,
        { _, hours: Int, minutes: Int ->
            onChange(hours * 60 + minutes)
        },
        hour,
        minute,
        true
    )
}