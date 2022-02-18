package ru.vlasilya.ledlamp.application.features.favorite

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.vlasilya.ledlamp.R
import ru.vlasilya.ledlamp.application.features.favorite.components.FavoriteEffectView
import ru.vlasilya.ledlamp.application.ui.containers.page.Page
import ru.vlasilya.ledlamp.application.ui.containers.page.TopBarAction
import ru.vlasilya.ledlamp.application.ui.text.Header
import ru.vlasilya.ledlamp.domain.model.Effect
import ru.vlasilya.ledlamp.domain.model.FavoriteEffect

@Composable
@ExperimentalMaterialApi
fun Favorite(viewModel: FavoriteViewModel) {
    val effects = viewModel.effects.observeAsState(initial = emptyList())

    FavoriteView(
        effects.value,
        { viewModel.delete(it) },
        { viewModel.setEffect(it) }
    )
}

@Composable
@ExperimentalMaterialApi
fun FavoriteView(
    effects: List<FavoriteEffect>,
    onDelete: (FavoriteEffect) -> Unit,
    onSelect: (FavoriteEffect) -> Unit,
) {
    Page(
        header = R.string.favorite_header,
        modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 55.dp)
    ) {
        LazyColumn {
            itemsIndexed(effects) { index, it ->
                FavoriteEffectView(
                    it,
                    onDelete = { onDelete(it) },
                    onClick = { onSelect(it) }
                )
                if (index != effects.size - 1) {
                    Divider(color = colorResource(R.color.colorTertiary))
                }
            }
        }
    }
}