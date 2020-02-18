package sample.kotlin.project.domain.stores.main.data

import sample.kotlin.project.domain.core.mvi.Action

sealed class MainAction : Action {

    object NavigateToSearchAction : MainAction()
}
