package ru.vlasilya.ledlamp.application.ui.controls

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import ru.vlasilya.ledlamp.R
import ru.vlasilya.ledlamp.application.ui.text.Title

@Composable
fun SliderWithTitle(
    title: Int,
    value: Int,
    minValue: Int,
    maxValue: Int,
    onChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var position by remember { mutableStateOf(0f) }
    position = value.toFloat()

    ConstraintLayout(modifier = modifier) {
        val (titleView, sliderView, imageMinusView, imagePlusView) = createRefs()

        Title(title, modifier = Modifier.constrainAs(titleView) { top.linkTo(parent.top) })

        Image(
            painter = painterResource(R.drawable.minus),
            contentDescription = null,
            modifier = Modifier
                .padding(10.dp, 0.dp, 0.dp, 0.dp)
                .width(5.dp)
                .height(5.dp)
                .constrainAs(imageMinusView) {
                    start.linkTo(parent.start)
                    top.linkTo(sliderView.top)
                    bottom.linkTo(sliderView.bottom)
                }
        )

        Image(
            painter = painterResource(R.drawable.plus),
            contentDescription = null,
            modifier = Modifier
                .padding(0.dp, 0.dp, 10.dp, 0.dp)
                .width(5.dp)
                .height(5.dp)
                .constrainAs(imagePlusView) {
                    top.linkTo(sliderView.top)
                    bottom.linkTo(sliderView.bottom)
                    end.linkTo(parent.end)
                }
        )

        Slider(
            value = position,
            valueRange = minValue.toFloat()..maxValue.toFloat(),
            onValueChange = {
                position = it
                onChange(it.toInt())
            },
            modifier = Modifier
                .padding(10.dp, 0.dp, 10.dp, 0.dp)
                .constrainAs(sliderView) {
                    top.linkTo(titleView.bottom)
                }
        )
    }
}

@Preview
@Composable
fun SliderWithTitlePreview() {
    SliderWithTitle(
        title = R.string.confetti_bright_name,
        value = 50,
        minValue = 0,
        maxValue = 100,
        onChange = {},
    )
}