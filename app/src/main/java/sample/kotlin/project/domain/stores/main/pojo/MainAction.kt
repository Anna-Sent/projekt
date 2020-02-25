package sample.kotlin.project.domain.stores.main.pojo

import sample.kotlin.project.domain.core.mvi.pojo.Action

sealed class MainAction : Action {

    object OnActivityCreatedFirstTime : MainAction()

    class OnConnectivityChanged(val isConnected: Boolean) : MainAction()
}
