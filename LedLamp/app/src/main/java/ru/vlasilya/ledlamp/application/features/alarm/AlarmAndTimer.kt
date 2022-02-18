package ru.vlasilya.ledlamp.application.features.alarm

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import ru.vlasilya.ledlamp.R
import ru.vlasilya.ledlamp.application.features.alarm.components.*
import ru.vlasilya.ledlamp.application.ui.text.Header

@Composable
@ExperimentalPagerApi
fun AlarmAndTimer(viewModel: AlarmAndTimerViewModel, context: Context) {
    val tabs = listOf(AlarmTab(viewModel, context), TimerTab(viewModel))
    val pagerState = rememberPagerState(pageCount = tabs.size)
    Scaffold(
        topBar = {
            TopAppBar {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Header(
                        R.string.alarm_header,
                    )
                }
            }
        },
    ) {
        Column {
            Tabs(tabs = tabs, pagerState = pagerState)
            TabsContent(tabs = tabs, pagerState = pagerState)
        }
    }
}
