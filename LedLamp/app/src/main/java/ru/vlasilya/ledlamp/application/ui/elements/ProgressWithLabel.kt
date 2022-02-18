package ru.vlasilya.ledlamp.application.ui.elements

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import ru.vlasilya.ledlamp.R

@Composable
fun ProgressWithLabel(
    label: Int,
    value: Int,
    minValue: Int,
    maxValue: Int,
    modifier: Modifier = Modifier
) {
    val max = maxValue - minValue
    val current = value - minValue
    ConstraintLayout(modifier = modifier.padding(0.dp, 5.dp)) {
        val (labelView, valueView) = createRefs()
        Text(
            text = stringResource(label),
            fontWeight = FontWeight.Light,
            modifier = Modifier.constrainAs(labelView) { top.linkTo(parent.top) }
        )
        LinearProgressIndicator(
            progress = current.toFloat() / max.toFloat(),
            modifier = Modifier.fillMaxWidth().constrainAs(valueView) {
                top.linkTo(labelView.bottom)
            },
        )
    }
}

@Preview
@Composable
fun ProgressWithLabelPreview() {
    ProgressWithLabel(
        R.string.blizzard_bright_name,
        100,
        50,
        150
    )
}