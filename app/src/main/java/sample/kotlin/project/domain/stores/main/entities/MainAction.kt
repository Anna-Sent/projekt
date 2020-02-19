package sample.kotlin.project.domain.stores.main.entities

import sample.kotlin.project.domain.core.mvi.entities.Action

sealed class MainAction : Action {

    object NavigateToInitialScreenAction : MainAction()

    class NetworkConnectedChanged(val isConnected: Boolean) : MainAction()
}
