package sample.kotlin.project.domain.stores.main

import sample.kotlin.project.domain.core.mvi.Store
import sample.kotlin.project.domain.stores.main.middlewares.NavigationMiddleware
import javax.inject.Inject

class MainStore
@Inject constructor(
    navigationMiddleware: NavigationMiddleware,
    initialState: MainState
) : Store<MainAction, MainState, MainEvent>(
    MainReducer(),
    setOf(
        navigationMiddleware
    ),
    initialState
)
