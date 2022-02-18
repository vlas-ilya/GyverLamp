package ru.vlasilya.ledlamp.application.ui.behaviour

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import ru.vlasilya.ledlamp.R

@Composable
@ExperimentalMaterialApi
fun SwipeToDelete(
    onDismiss: () -> Unit,
    content: @Composable RowScope.() -> Unit,
) {
    val dismissState = rememberDismissState(
        confirmStateChange = {
            if (it == DismissValue.DismissedToStart) {
                onDismiss()
            }
            it != DismissValue.DismissedToStart
        }
    )

    SwipeToDismiss(
        state = dismissState,
        modifier = Modifier.padding(vertical = 0.dp),
        directions = setOf(DismissDirection.EndToStart),
        dismissThresholds = { direction ->
            FractionalThreshold(if (direction == DismissDirection.StartToEnd) 0.25f else 0.5f)
        },
        background = {
            val direction = dismissState.dismissDirection ?: return@SwipeToDismiss
            val color by animateColorAsState(
                when (dismissState.targetValue) {
                    DismissValue.Default -> colorResource(id = R.color.colorTertiary)
                    DismissValue.DismissedToEnd -> colorResource(id = R.color.success)
                    DismissValue.DismissedToStart -> colorResource(id = R.color.error)
                }
            )

            val iconColor by animateColorAsState(
                when (dismissState.targetValue) {
                    DismissValue.Default -> colorResource(id = R.color.colorOnTertiary)
                    DismissValue.DismissedToEnd -> colorResource(id = R.color.onSuccess)
                    DismissValue.DismissedToStart -> colorResource(id = R.color.onError)
                }
            )

            val alignment = when (direction) {
                DismissDirection.StartToEnd -> Alignment.CenterStart
                DismissDirection.EndToStart -> Alignment.CenterEnd
            }
            val icon = when (direction) {
                DismissDirection.StartToEnd -> Icons.Default.Done
                DismissDirection.EndToStart -> Icons.Default.Delete
            }
            val scale by animateFloatAsState(
                if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
            )
            Box(
                Modifier
                    .fillMaxSize()
                    .background(color)
                    .padding(horizontal = 20.dp),
                contentAlignment = alignment
            ) {
                Icon(
                    icon,
                    contentDescription = "",
                    modifier = Modifier.scale(scale),
                    tint = iconColor
                )
            }
        },
        dismissContent = {
            Card(
                modifier = Modifier.padding(0.dp),
                elevation = animateDpAsState(
                    if (dismissState.dismissDirection != null) 4.dp else 0.dp
                ).value
            ) {
                ListItem(
                    modifier = Modifier.padding(0.dp),
                    text = { content() },
                )
            }
        }
    )
}