package sample.kotlin.project.domain.stores.main

import sample.kotlin.project.domain.core.mvi.Store
import sample.kotlin.project.domain.stores.main.entities.MainAction
import sample.kotlin.project.domain.stores.main.entities.MainEvent
import sample.kotlin.project.domain.stores.main.entities.MainState
import sample.kotlin.project.domain.stores.main.middlewares.NavigationMiddleware
import sample.kotlin.project.domain.stores.main.middlewares.NetworkConnectivityMiddleware
import javax.inject.Inject

class MainStore
@Inject constructor(
    navigationMiddleware: NavigationMiddleware,
    networkConnectivityMiddleware: NetworkConnectivityMiddleware,
    initialState: MainState
) : Store<MainAction, MainState, MainEvent>(
    MainReducer(),
    setOf(
        navigationMiddleware,
        networkConnectivityMiddleware
    ),
    initialState
)
