package sample.kotlin.project.domain.stores.main

import sample.kotlin.project.domain.core.mvi.Store
import sample.kotlin.project.domain.sources.core.schedulers.SchedulersProvider
import sample.kotlin.project.domain.stores.main.entities.MainAction
import sample.kotlin.project.domain.stores.main.entities.MainEvent
import sample.kotlin.project.domain.stores.main.entities.MainNavigationCommand
import sample.kotlin.project.domain.stores.main.entities.MainState
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
