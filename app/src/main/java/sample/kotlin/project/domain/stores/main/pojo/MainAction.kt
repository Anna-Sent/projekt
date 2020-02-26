package sample.kotlin.project.domain.stores.main.pojo

import sample.kotlin.project.domain.core.mvi.pojo.Action

sealed class MainAction : Action {

    object NavigateToFirstScreen : MainAction()

    data class OnConnectivityChanged(val isConnected: Boolean) : MainAction()
}
