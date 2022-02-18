package ru.vlasilya.ledlamp.application.features.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import ru.vlasilya.ledlamp.R
import ru.vlasilya.ledlamp.application.ui.containers.page.Page
import ru.vlasilya.ledlamp.application.ui.containers.page.TopBarAction
import ru.vlasilya.ledlamp.application.ui.controls.BigButton

@Composable
fun Settings(viewModel: SettingsViewModel) {
    val ipAddress = viewModel.ipAddress.observeAsState(initial = "").value
    val port = viewModel.port.observeAsState(initial = 8888).value
    val lamps = viewModel.lamps.observeAsState(initial = emptyList()).value
    val needToSave = viewModel.needToSave.observeAsState(initial = false).value

    SettingsView(
        ipAddress = ipAddress,
        port = port,
        lamps = lamps,
        needToSave = needToSave,
        onChangeIpAddress = { viewModel.changeIpAddress(it) },
        onChangePort = { viewModel.onChangePort(it) },
        onSave = { viewModel.save() },
        onRefresh = { viewModel.refreshIpList(port) }
    )
}

@Composable
fun SettingsView(
    ipAddress: String,
    port: Int,
    lamps: List<Pair<String, Int>>,
    needToSave: Boolean,
    onChangeIpAddress: (String) -> Unit,
    onChangePort: (Int) -> Unit,
    onSave: () -> Unit,
    onRefresh: () -> Unit,
) {
    Page(
        header = R.string.settings_header,
        actions = listOf {
            TopBarAction(
                icon = R.drawable.loading,
                onClick = { onRefresh() }
            )
        }
    ) {
        val (settingsView, listView) = createRefs()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(settingsView) { top.linkTo(parent.top) }
                .background(colorResource(R.color.colorTertiaryVariant))
        ) {
            Image(
                painter = painterResource(R.drawable.carousel_background),
                contentDescription = null,
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.FillHeight,
                alpha = 0.1f
            )
            ConstraintLayout() {
                val (ipView, portView, saveButton) = createRefs()

                TextField(
                    value = ipAddress,
                    onValueChange = { onChangeIpAddress(it) },
                    label = { Text(stringResource(R.string.ip)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 8.dp)
                        .constrainAs(ipView) { top.linkTo(parent.top) }
                )
                TextField(
                    value = port.toString(),
                    onValueChange = { onChangePort(it.filter { ch -> ch.isDigit() }.toInt()) },
                    label = { Text(stringResource(R.string.port)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 8.dp)
                        .constrainAs(portView) { top.linkTo(ipView.bottom) }
                )
                if (needToSave) {
                    BigButton(
                        label = R.string.save_settings,
                        onClick = { onSave() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp, 8.dp)
                            .constrainAs(saveButton) { top.linkTo(portView.bottom) }
                    )
                }
            }
        }
        LazyColumn(modifier = Modifier.constrainAs(listView) { top.linkTo(settingsView.bottom) }) {
            items(lamps) {
                Text(
                    text = "${it.first}:${it.second}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onChangeIpAddress(it.first)
                            onChangePort(it.second)
                            onSave()
                        }
                        .padding(10.dp, 10.dp),
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Light
                )
                Divider(color = colorResource(R.color.colorTertiary))
            }
        }
    }
}