package sample.kotlin.project.domain.stores.splash

import sample.kotlin.project.domain.core.mvi.Store
import sample.kotlin.project.domain.sources.core.schedulers.SchedulersProvider
import sample.kotlin.project.domain.stores.splash.entities.SplashAction
import sample.kotlin.project.domain.stores.splash.entities.SplashEvent
import sample.kotlin.project.domain.stores.splash.entities.SplashNavigationCommand
import sample.kotlin.project.domain.stores.splash.entities.SplashState
import sample.kotlin.project.domain.stores.splash.middlewares.SplashMiddleware
import javax.inject.Inject

class SplashStore
@Inject constructor(
    schedulersProvider: SchedulersProvider,
    splashMiddleware: SplashMiddleware,
    initialState: SplashState
) : Store<SplashState, SplashAction, SplashEvent, SplashNavigationCommand>(
    schedulersProvider,
    SplashReducer(),
    setOf(
        splashMiddleware
    ),
    initialState
)
