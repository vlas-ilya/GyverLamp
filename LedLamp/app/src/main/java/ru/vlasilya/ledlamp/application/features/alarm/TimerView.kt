package ru.vlasilya.ledlamp.application.features.alarm

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import ru.vlasilya.ledlamp.R
import ru.vlasilya.ledlamp.application.ui.controls.BigButton
import ru.vlasilya.ledlamp.application.ui.controls.TimePicker
import ru.vlasilya.ledlamp.domain.model.Timer
import ru.vlasilya.ledlamp.domain.model.TimerState

@Composable
fun TimerView(viewModel: AlarmAndTimerViewModel) {
    val timer = viewModel.timer.observeAsState(initial = Timer(TimerState.OFF, 0, 0))

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(10.dp, 10.dp, 10.dp, 60.dp)
    ) {
        val (picker, run) = createRefs()
        TimePicker(
            modifier = Modifier.constrainAs(picker) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            disabled = timer.value.state == TimerState.ON,
            value = timer.value.time,
            onChange = { viewModel.setTimer(it) }
        )
        BigButton(
            label = if (timer.value.state == TimerState.OFF) R.string.start_timer else R.string.stop_timer,
            onClick = { if (timer.value.state == TimerState.OFF) viewModel.startTimer() else viewModel.stopTimer() },
            modifier = Modifier.fillMaxWidth().padding(0.dp, 8.dp).constrainAs(run) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            },
        )
    }
}
