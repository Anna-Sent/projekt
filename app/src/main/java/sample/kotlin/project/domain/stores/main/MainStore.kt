package sample.kotlin.project.domain.stores.main

import sample.kotlin.project.domain.core.mvi.Store
import javax.inject.Inject

class MainStore
@Inject constructor(
    initialState: MainState
) : Store<MainAction, MainState, MainEvent>(
    MainReducer(),
    emptySet(),
    initialState
)
