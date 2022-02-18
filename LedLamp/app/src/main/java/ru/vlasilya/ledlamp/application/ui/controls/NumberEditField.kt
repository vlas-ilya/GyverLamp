package ru.vlasilya.ledlamp.application.ui.controls

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import ru.vlasilya.ledlamp.R
import ru.vlasilya.ledlamp.application.ui.text.BigText
import ru.vlasilya.ledlamp.application.ui.text.SmallText

@Composable
fun NumberEditField(
    modifier: Modifier  = Modifier,
    value: Int,
    minValue: Int,
    maxValue: Int,
    label: Int? = null,
    fillZero: Int = 0,
    disabled: Boolean = false,
    onChange: (Int) -> Unit
) {
    var text = value.toString()
    while (text.length < fillZero) {
        text = "0$text"
    }

    ConstraintLayout(
        modifier = modifier
    ) {
        val (up, valueView, down, labelView) = createRefs()
        Button(
            enabled = !disabled,
            onClick = { onChange(if (value + 1 > maxValue) minValue else value + 1) },
            modifier = Modifier.constrainAs(up) {
                top.linkTo(parent.top)
                bottom.linkTo(valueView.top)
                start.linkTo(valueView.start)
                end.linkTo(valueView.end)
            }
        ) {
            Icon(
                painter = painterResource(R.drawable.up),
                contentDescription = null,
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
                    .padding(15.dp)
            )
        }
        BigText(
            text = text,
            modifier = Modifier.constrainAs(valueView) {
                top.linkTo(up.bottom)
                bottom.linkTo(down.top)
            }
        )
        Button(
            enabled = !disabled,
            onClick = { onChange(if (value - 1 < minValue) maxValue else value - 1) },
            modifier = Modifier.constrainAs(down) {
                top.linkTo(valueView.bottom)
                bottom.linkTo(labelView.top)
                start.linkTo(valueView.start)
                end.linkTo(valueView.end)
            }
        ) {
            Icon(
                painter = painterResource(R.drawable.down),
                contentDescription = null,
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
                    .padding(15.dp)
            )
        }
        if (label != null) {
            SmallText(
                res = label,
                modifier = Modifier.constrainAs(labelView) {
                    top.linkTo(up.bottom)
                    start.linkTo(valueView.start)
                    end.linkTo(valueView.end)
                    bottom.linkTo(parent.bottom)
                }
            )
        }
    }
}


@Preview
@Composable
fun NumberEditFieldPreview() {
    var value by remember { mutableStateOf(0) }

    NumberEditField(
        value = value,
        minValue = 0,
        maxValue = 20,
        fillZero = 2,
        disabled = false,
        onChange = { value = it}
    )
}