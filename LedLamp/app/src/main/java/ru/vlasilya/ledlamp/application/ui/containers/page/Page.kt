package ru.vlasilya.ledlamp.application.ui.containers.page

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import ru.vlasilya.ledlamp.R
import ru.vlasilya.ledlamp.application.ui.text.Header

@Composable
inline fun Page(
    header: Int,
    actions: List<@Composable () -> Unit> = ArrayList(),
    loading: Boolean = false,
    modifier: Modifier = Modifier,
    crossinline content: @Composable ConstraintLayoutScope.() -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Header(header)
                    Row (
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.wrapContentWidth()
                    ) {
                        actions.map { it() }
                    }
                }
            }
        },
    ) {
        ConstraintLayout(modifier) {
            if (!loading) {
                content()
            }
        }
    }
}

@Preview
@Composable
fun PagePreview() {
    Page(
        header = R.string.effects_header,
        actions = listOf {
            TopBarAction(
                icon = R.drawable.favorite_route_icon,
                onClick = {}
            )
        }
    ) {

    }
}