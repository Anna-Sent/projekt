package sample.kotlin.project.domain.stores.main

import sample.kotlin.project.domain.core.mvi.Reducer
import sample.kotlin.project.domain.stores.main.pojo.MainAction
import sample.kotlin.project.domain.stores.main.pojo.MainState

internal class MainReducer : Reducer<MainState, MainAction> {

    override fun reduce(state: MainState, action: MainAction) = state
}
