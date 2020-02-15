package sample.kotlin.project.domain.stores.main

import sample.kotlin.project.domain.core.mvi.Reducer

class MainReducer : Reducer<MainState, MainAction> {

    override fun reduce(state: MainState, action: MainAction) = state
}
