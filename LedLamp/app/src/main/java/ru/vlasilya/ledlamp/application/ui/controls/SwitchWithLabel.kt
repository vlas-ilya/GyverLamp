package ru.vlasilya.ledlamp.application.ui.controls

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import ru.vlasilya.ledlamp.application.ui.text.Span

@Composable
fun SwitchWithLabel(
    label: Int,
    checked: Boolean,
    onSwitch: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = modifier.fillMaxWidth()
    ) {
        val (title, switcher) = createRefs()
        Span(
            label,
            modifier = Modifier.constrainAs(title) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
        )
        Switch(
            checked = checked,
            modifier = Modifier.constrainAs(switcher) {
                end.linkTo(parent.end)
                top.linkTo(title.top)
                bottom.linkTo(title.bottom)
            },
            onCheckedChange = { onSwitch(it) }
        )
    }
}