package sample.kotlin.project.domain.stores.splash

import io.reactivex.Scheduler
import sample.kotlin.project.domain.core.mvi.Store
import sample.kotlin.project.domain.stores.splash.entities.SplashAction
import sample.kotlin.project.domain.stores.splash.entities.SplashEvent
import sample.kotlin.project.domain.stores.splash.entities.SplashNavigationCommand
import sample.kotlin.project.domain.stores.splash.entities.SplashState
import sample.kotlin.project.domain.stores.splash.middlewares.SplashMiddleware
import javax.inject.Inject

class SplashStore
@Inject constructor(
    uiScheduler: Scheduler,
    splashMiddleware: SplashMiddleware,
    initialState: SplashState
) : Store<SplashState, SplashAction, SplashEvent, SplashNavigationCommand>(
    uiScheduler,
    SplashReducer(),
    setOf(
        splashMiddleware
    ),
    initialState
)
