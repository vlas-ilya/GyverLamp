package ru.vlasilya.ledlamp.application.ui.controls

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import ru.vlasilya.ledlamp.application.ui.text.Span

data class Element(
    val id: Int,
    val label: Int
)

@Composable
fun MenuWithLabel(
    label: Int,
    currentElement: Element,
    elements: List<Element>,
    onSelect: (Element) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }
    ConstraintLayout(
        modifier = modifier.fillMaxWidth()
    ) {
        val (title, list) = createRefs()
        Span(
            label,
            modifier = Modifier.constrainAs(title) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
        )
        Box(
            modifier = Modifier.constrainAs(list) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
            }
        ) {
            Button(onClick = { expanded = true }) {
                Text(
                    text = stringResource(currentElement.label),
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                elements.forEach {
                    DropdownMenuItem(onClick = {
                        expanded = false
                        onSelect(it)
                    }) {
                        Span(it.label)
                    }
                }
            }
        }
    }
}