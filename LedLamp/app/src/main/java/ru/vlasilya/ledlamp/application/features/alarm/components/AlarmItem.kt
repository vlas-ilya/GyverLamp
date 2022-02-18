package ru.vlasilya.ledlamp.application.features.alarm.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import ru.vlasilya.ledlamp.application.ui.controls.SmallTimePicker
import ru.vlasilya.ledlamp.application.ui.text.Span
import ru.vlasilya.ledlamp.domain.model.AlarmDay
import ru.vlasilya.ledlamp.domain.model.AlarmDaySate
import ru.vlasilya.ledlamp.domain.model.Mon

@Composable
fun AlarmItem(
    alarmDay: AlarmDay,
    modifier: Modifier = Modifier,
    switchAlarm: (Boolean) -> Unit,
    changeAlarm: () -> Unit
) {
    ConstraintLayout(
        modifier = modifier.fillMaxWidth()
    ) {
        val (title, switcher, value) = createRefs()
        Span(
            alarmDay.title,
            modifier = Modifier.constrainAs(title) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
        )
        Switch(
            checked = alarmDay.state == AlarmDaySate.ON,
            modifier = Modifier.constrainAs(switcher) {
                end.linkTo(value.start)
                top.linkTo(title.top)
                bottom.linkTo(title.bottom)
            },
            onCheckedChange = { switchAlarm(it) }
        )
        SmallTimePicker(
            alarmDay.time,
            modifier = Modifier.constrainAs(value) {
                end.linkTo(parent.end)
                top.linkTo(title.top)
                bottom.linkTo(title.bottom)
            },
            onClick = { changeAlarm() }
        )
    }
}

@Preview
@Composable
fun AlarmItemPreview() {
    AlarmItem(
        alarmDay = Mon(AlarmDaySate.ON, 500),
        changeAlarm = {},
        switchAlarm = {}
    )
}