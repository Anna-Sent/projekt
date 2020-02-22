package sample.kotlin.project.domain.stores.main

import sample.kotlin.project.domain.core.mvi.Store
import sample.kotlin.project.domain.providers.schedulers.SchedulersProvider
import sample.kotlin.project.domain.stores.main.pojo.MainAction
import sample.kotlin.project.domain.stores.main.pojo.MainEvent
import sample.kotlin.project.domain.stores.main.pojo.MainNavigationCommand
import sample.kotlin.project.domain.stores.main.pojo.MainState
import sample.kotlin.project.domain.stores.main.middlewares.ConnectivityMiddleware
import sample.kotlin.project.domain.stores.main.middlewares.NavigationMiddleware
import javax.inject.Inject

class MainStore
@Inject constructor(
    schedulersProvider: SchedulersProvider,
    navigationMiddleware: NavigationMiddleware,
    connectivityMiddleware: ConnectivityMiddleware,
    initialState: MainState
) : Store<MainState, MainAction, MainEvent, MainNavigationCommand>(
    schedulersProvider,
    MainReducer(),
    setOf(
        navigationMiddleware,
        connectivityMiddleware
    ),
    initialState
)
