package sample.kotlin.project.domain.stores.splash

import sample.kotlin.project.domain.core.mvi.Store
import sample.kotlin.project.domain.providers.schedulers.SchedulersProvider
import sample.kotlin.project.domain.stores.splash.middlewares.SplashMiddleware
import sample.kotlin.project.domain.stores.splash.pojo.SplashAction
import sample.kotlin.project.domain.stores.splash.pojo.SplashEvent
import sample.kotlin.project.domain.stores.splash.pojo.SplashNavigationCommand
import sample.kotlin.project.domain.stores.splash.pojo.SplashState
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
