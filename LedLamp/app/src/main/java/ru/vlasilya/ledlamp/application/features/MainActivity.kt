package ru.vlasilya.ledlamp.application.features

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import ru.vlasilya.ledlamp.R
import ru.vlasilya.ledlamp.application.context.ApplicationContext
import ru.vlasilya.ledlamp.application.context.DependencyGraph
import ru.vlasilya.ledlamp.application.features.alarm.AlarmAndTimer
import ru.vlasilya.ledlamp.application.features.alarm.AlarmAndTimerViewModel
import ru.vlasilya.ledlamp.application.features.carousel.Carousel
import ru.vlasilya.ledlamp.application.features.carousel.CarouselViewModel
import ru.vlasilya.ledlamp.application.features.favorite.Favorite
import ru.vlasilya.ledlamp.application.features.favorite.FavoriteViewModel
import ru.vlasilya.ledlamp.application.features.effects.Effects
import ru.vlasilya.ledlamp.application.features.effects.EffectsViewModel
import ru.vlasilya.ledlamp.application.features.settings.Settings
import ru.vlasilya.ledlamp.application.features.settings.SettingsViewModel
import ru.vlasilya.ledlamp.application.ui.elements.MenuIcon
import ru.vlasilya.ledlamp.domain.model.LampState

@ExperimentalMaterialApi
@ExperimentalPagerApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: MainViewModel by viewModels {
            DependencyGraph.MainViewModelFactory((application as ApplicationContext).dependencies())
        }
        lifecycle.addObserver(viewModel)
        setContent {
            App(viewModel)
        }
    }

    @Composable
    fun App(viewModel: MainViewModel) {
        val state = viewModel.lampState.observeAsState(initial = LampState.OFF)
        val isOnline = viewModel.isOnline.observeAsState(initial = false)

        val navController = rememberNavController()
        val scaffoldState = rememberScaffoldState()

        val bottomNavigationRoutes: List<Route> = arrayListOf(
            Routes.effects,
            Routes.favorite,
            Routes.carousel,
            Routes.alarm,
        )

        MaterialTheme(
            colors = Colors(
                primary = colorResource(R.color.colorPrimary),
                primaryVariant = colorResource(R.color.colorPrimaryVariant),
                secondary = colorResource(R.color.colorSecondary),
                secondaryVariant = colorResource(R.color.colorSecondaryVariant),
                background = colorResource(R.color.background),
                surface = colorResource(R.color.surface),
                error = colorResource(R.color.error),
                onPrimary = colorResource(R.color.colorOnPrimary),
                onSecondary = colorResource(R.color.colorOnSecondary),
                onBackground = colorResource(R.color.onBackground),
                onSurface = colorResource(R.color.onSurface),
                onError = colorResource(R.color.onError),
                isLight = true,
            )
        ) {
            Scaffold(
                scaffoldState = scaffoldState,
                isFloatingActionButtonDocked = true,
                floatingActionButton = {
                    val backgroundColor = colorResource(
                        if (!isOnline.value) R.color.offline_background
                        else if (state.value == LampState.OFF) R.color.plug_off_background
                        else R.color.plug_on_background
                    )
                    val contentColor = colorResource(
                        if (!isOnline.value) R.color.offline_content
                        else if (state.value == LampState.OFF) R.color.plug_off_content
                        else R.color.plug_on_content
                    )
                    val icon = painterResource(
                        if (isOnline.value) R.drawable.plug
                        else R.drawable.plug_2
                    )
                    FloatingActionButton(
                        backgroundColor = backgroundColor,
                        contentColor = contentColor,
                        onClick = {
                            if (isOnline.value) viewModel.toggleLamp()
                            else navController.navigate(Routes.settings.id)
                        }
                    ) {
                        Icon(
                            painter = icon,
                            contentDescription = null,
                            modifier = Modifier
                                .width(50.dp)
                                .height(50.dp)
                                .padding(10.dp)
                        )
                    }
                },
                bottomBar = {
                    BottomAppBar(
                        cutoutShape = MaterialTheme.shapes.small.copy(
                            CornerSize(percent = 50)
                        )
                    ) {
                        Row(Modifier.padding(0.dp, 0.dp, 70.dp, 0.dp)) {
                            val currentRoute = navController.currentBackStackEntry?.destination?.route
                            bottomNavigationRoutes.forEach { route ->
                                BottomNavigationItem(
                                    icon = { MenuIcon(route.icon, currentRoute == route.id) },
                                    label = { Text(getString(route.label)) },
                                    selected = currentRoute == route.id,
                                    alwaysShowLabel = false,
                                    onClick = {
                                        if (currentRoute != route.id) {
                                            navController.navigate(route.id)
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            ) {
                NavHost(navController, startDestination = Routes.effects.id) {
                    composable(Routes.effects.id) { EffectsRoute(it, navController) }
                    composable(Routes.favorite.id) { FavoriteRoute(it) }
                    composable(Routes.carousel.id) { CarouselRoute(it) }
                    composable(Routes.alarm.id) { AlarmAndTimerRoute(it) }
                    composable(Routes.settings.id) { SettingsRoute(it) }
                }
            }
        }
    }

    private val effectsViewModel: EffectsViewModel by viewModels {
        DependencyGraph.EffectsViewModelFactory((application as ApplicationContext).dependencies())
    }

    @Composable
    fun EffectsRoute(nav: NavBackStackEntry, navController: NavHostController) {
        if (nav.maxLifecycle == Lifecycle.State.RESUMED) {
            effectsViewModel.onResume()
        }
        Effects(effectsViewModel) { navController.navigate(Routes.settings.id) }
    }

    private val favoriteViewModel: FavoriteViewModel by viewModels {
        DependencyGraph.FavoriteViewModelFactory((application as ApplicationContext).dependencies())
    }

    @Composable
    fun FavoriteRoute(nav: NavBackStackEntry) {
        if (nav.maxLifecycle == Lifecycle.State.RESUMED) {
            favoriteViewModel.onResume()
        }
        Favorite(favoriteViewModel)
    }


    private val carouselViewModel: CarouselViewModel by viewModels {
        DependencyGraph.CarouselViewModelFactory((application as ApplicationContext).dependencies())
    }

    @Composable
    fun CarouselRoute(nav: NavBackStackEntry) {
        if (nav.maxLifecycle == Lifecycle.State.RESUMED) {
            carouselViewModel.onResume()
        }
        Carousel(carouselViewModel)
    }

    private val alarmAndTimerViewModel: AlarmAndTimerViewModel by viewModels {
        DependencyGraph.AlarmAndTimerViewModelFactory((application as ApplicationContext).dependencies())
    }

    @Composable
    fun AlarmAndTimerRoute(nav: NavBackStackEntry) {
        if (nav.maxLifecycle == Lifecycle.State.RESUMED) {
            alarmAndTimerViewModel.onResume()
        }
        AlarmAndTimer(alarmAndTimerViewModel, this)
    }

    private val settingsViewModel: SettingsViewModel by viewModels {
        DependencyGraph.SettingsViewModelFactory((application as ApplicationContext).dependencies())
    }

    @Composable
    fun SettingsRoute(nav: NavBackStackEntry) {
        if (nav.maxLifecycle == Lifecycle.State.RESUMED) {
            settingsViewModel.onResume()
        }
        Settings(settingsViewModel)
    }
}
