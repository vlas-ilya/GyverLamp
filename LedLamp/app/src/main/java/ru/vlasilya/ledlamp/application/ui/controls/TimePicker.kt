package ru.vlasilya.ledlamp.application.ui.controls

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import ru.vlasilya.ledlamp.R

@Composable
fun TimePicker(
    modifier: Modifier = Modifier,
    value: Int,
    disabled: Boolean,
    onChange: (Int) -> Unit
) {
    val min = value / 60
    val sec = value % 60

    ConstraintLayout(
        modifier.wrapContentHeight()
    ) {
        val (minutes, seconds) = createRefs()
        NumberEditField(
            modifier = Modifier.padding(10.dp).constrainAs(minutes) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(seconds.start)
            },
            disabled = disabled,
            minValue = 0,
            maxValue = 60,
            value = min,
            onChange = { onChange(it * 60 + sec) },
            fillZero = 2,
            label = R.string.minutes
        )
        NumberEditField(
            modifier = Modifier.padding(10.dp).constrainAs(seconds) {
                top.linkTo(parent.top)
                start.linkTo(minutes.end)
                end.linkTo(parent.end)
            },
            disabled = disabled,
            minValue = 0,
            maxValue = 59,
            value = sec,
            onChange = { onChange(min * 60 + it) },
            fillZero = 2,
            label = R.string.seconds
        )
    }
}

@Preview
@Composable
fun TimePickerPreview() {
    TimePicker(
        value = 356,
        disabled = false,
        onChange = {}
    )
}