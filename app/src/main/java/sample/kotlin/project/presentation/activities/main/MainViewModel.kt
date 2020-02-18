package sample.kotlin.project.presentation.activities.main

import sample.kotlin.project.domain.stores.main.entities.MainAction
import sample.kotlin.project.domain.stores.main.entities.MainEvent
import sample.kotlin.project.domain.stores.main.entities.MainState
import sample.kotlin.project.domain.stores.main.MainStore
import sample.kotlin.project.presentation.core.BaseViewModel
import javax.inject.Inject

class MainViewModel
@Inject constructor(
    store: MainStore
) : BaseViewModel<MainState, MainAction, MainEvent>(store)
