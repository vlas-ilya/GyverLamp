package ru.vlasilya.ledlamp.application.features.favorite.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import ru.vlasilya.ledlamp.application.ui.behaviour.SwipeToDelete
import ru.vlasilya.ledlamp.application.ui.behaviour.Visibility
import ru.vlasilya.ledlamp.application.ui.elements.ProgressWithLabel
import ru.vlasilya.ledlamp.domain.model.FavoriteEffect


@Composable
@ExperimentalMaterialApi
fun FavoriteEffectView(
    effect: FavoriteEffect,
    onDelete: () -> Unit,
    onClick: () -> Unit
) {
    SwipeToDelete(onDismiss = { onDelete() }) {
        ConstraintLayout(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 5.dp).clickable { onClick() }) {
            val (title, bright, speed, scale) = createRefs()
            Text(
                text = stringResource(effect.effect.name),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .constrainAs(title) { top.linkTo(parent.top) }
                    .padding(0.dp, 8.dp, 0.dp, 5.dp)
            )
            Visibility(
                visibly = !effect.effect.bright.notWorked,
                modifier = Modifier.constrainAs(bright) { top.linkTo(title.bottom) }
            ) {
                ProgressWithLabel(
                    effect.effect.bright.name,
                    effect.effect.bright.value,
                    effect.effect.bright.minValue,
                    effect.effect.bright.maxValue,
                )
            }
            Visibility(
                visibly = !effect.effect.speed.notWorked,
                modifier = Modifier.constrainAs(speed) { top.linkTo(bright.bottom) }
            ) {
                ProgressWithLabel(
                    effect.effect.speed.name,
                    effect.effect.speed.value,
                    effect.effect.speed.minValue,
                    effect.effect.speed.maxValue,
                )
            }
            Visibility(
                visibly = !effect.effect.scale.notWorked,
                modifier = Modifier.constrainAs(scale) { top.linkTo(speed.bottom) }
            ) {
                ProgressWithLabel(
                    effect.effect.scale.name,
                    effect.effect.scale.value,
                    effect.effect.scale.minValue,
                    effect.effect.scale.maxValue,
                )
            }
        }
    }
}