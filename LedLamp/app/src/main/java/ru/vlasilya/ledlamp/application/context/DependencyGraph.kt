package ru.vlasilya.ledlamp.application.context

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.vlasilya.ledlamp.application.features.MainViewModel
import ru.vlasilya.ledlamp.application.features.alarm.AlarmAndTimerViewModel
import ru.vlasilya.ledlamp.application.features.carousel.CarouselViewModel
import ru.vlasilya.ledlamp.application.features.effects.EffectsViewModel
import ru.vlasilya.ledlamp.application.features.favorite.FavoriteViewModel
import ru.vlasilya.ledlamp.application.features.settings.SettingsViewModel
import ru.vlasilya.ledlamp.infrastructure.dao.FavoriteEffectsDao
import ru.vlasilya.ledlamp.infrastructure.dao.FavoriteEffectsDatabase
import ru.vlasilya.ledlamp.infrastructure.events.EventDispatcher
import ru.vlasilya.ledlamp.infrastructure.events.impl.EventDispatcherImpl
import ru.vlasilya.ledlamp.infrastructure.network.NetworkFacade
import ru.vlasilya.ledlamp.infrastructure.network.NetworkScanner
import ru.vlasilya.ledlamp.infrastructure.network.impl.NetworkFacadeImpl
import ru.vlasilya.ledlamp.infrastructure.network.impl.NetworkScannerImpl
import ru.vlasilya.ledlamp.infrastructure.repositories.EffectRepository
import ru.vlasilya.ledlamp.infrastructure.repositories.FavoriteEffectsRepository
import ru.vlasilya.ledlamp.infrastructure.repositories.LampRepository
import ru.vlasilya.ledlamp.infrastructure.repositories.SettingsRepository
import ru.vlasilya.ledlamp.infrastructure.repositories.impl.EffectRepositoryImpl
import ru.vlasilya.ledlamp.infrastructure.repositories.impl.FavoriteEffectsRepositoryImpl
import ru.vlasilya.ledlamp.infrastructure.repositories.impl.LampRepositoryImpl
import ru.vlasilya.ledlamp.infrastructure.repositories.impl.SettingsRepositoryImpl
import ru.vlasilya.ledlamp.infrastructure.services.*
import ru.vlasilya.ledlamp.infrastructure.services.impl.*

class DependencyGraph(
    private val context: ApplicationContext
) {

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences("SHARED_PREFERENCES", Application.MODE_PRIVATE)
    }

    private val effectRepository: EffectRepository by lazy {
        EffectRepositoryImpl(sharedPreferences)
    }

    private val effectsService: EffectsService by lazy {
        EffectsServiceImpl(effectRepository)
    }

    private val lampRepository: LampRepository by lazy {
        LampRepositoryImpl(sharedPreferences)
    }

    private val networkFacade: NetworkFacade by lazy {
        NetworkFacadeImpl(settingsService, eventDispatcher)
    }

    private val lampService: LampService by lazy {
        LampServiceImpl(
            lampRepository,
            networkFacade,
            OptimisticLock
        )
    }

    private val eventDispatcher: EventDispatcher by lazy {
        EventDispatcherImpl()
    }

    private val synchroniseLampService: SynchroniseLampService by lazy {
        SynchroniseLampServiceImpl(
            lampRepository,
            eventDispatcher,
        )
    }

    private val poolingService: LampPullService by lazy {
        LampPullServiceImpl(
            networkFacade,
            synchroniseLampService,
            OptimisticLock
        )
    }

    private val favoriteEffectsDao: FavoriteEffectsDao by lazy {
        FavoriteEffectsDatabase.getInstance(context).favoriteEffectsDao
    }

    private val favoriteEffectsRepository: FavoriteEffectsRepository by lazy {
        FavoriteEffectsRepositoryImpl(favoriteEffectsDao)
    }

    private val favoriteEffectsService: FavoriteEffectsServiceImpl by lazy {
        FavoriteEffectsServiceImpl(favoriteEffectsRepository)
    }

    class EffectsViewModelFactory (
        private val dependencies: DependencyGraph,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(EffectsViewModel::class.java)) {
                return EffectsViewModel(
                    dependencies.effectsService,
                    dependencies.lampService,
                    dependencies.favoriteEffectsService,
                    OptimisticLock
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    class MainViewModelFactory (
        private val dependencies: DependencyGraph,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                return MainViewModel(
                    dependencies.lampService,
                    dependencies.poolingService,
                    dependencies.eventDispatcher
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    class FavoriteViewModelFactory (
        private val dependencies: DependencyGraph,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
                return FavoriteViewModel(
                    dependencies.favoriteEffectsService,
                    dependencies.lampService,
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    class CarouselViewModelFactory (
        private val dependencies: DependencyGraph,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CarouselViewModel::class.java)) {
                return CarouselViewModel(
                    dependencies.lampService
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    class AlarmAndTimerViewModelFactory (
        private val dependencies: DependencyGraph,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlarmAndTimerViewModel::class.java)) {
                return AlarmAndTimerViewModel(
                    dependencies.lampService,
                    dependencies.eventDispatcher
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    private val settingsRepository: SettingsRepository by lazy {
        SettingsRepositoryImpl(sharedPreferences)
    }

    private val settingsService: SettingsService by lazy {
        SettingsServiceImpl(settingsRepository, eventDispatcher)
    }

    private val networkScanner: NetworkScanner by lazy {
        NetworkScannerImpl()
    }

    class SettingsViewModelFactory (
        private val dependencies: DependencyGraph,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
                return SettingsViewModel(
                    dependencies.settingsService,
                    dependencies.networkScanner
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}