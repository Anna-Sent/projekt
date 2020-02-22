package sample.kotlin.project.domain.stores.main.pojo

import sample.kotlin.project.domain.core.mvi.pojo.Action

sealed class MainAction : Action {

    object NavigateToInitialScreenAction : MainAction()

    class NetworkConnectedChanged(val isConnected: Boolean) : MainAction()
}
