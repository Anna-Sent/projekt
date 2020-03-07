package sample.kotlin.project.domain.stores.main.pojo

import sample.kotlin.project.domain.core.mvi.pojo.NavigationCommand

sealed class MainNavigationCommand : NavigationCommand {

    object NavigateToSplashScreen : MainNavigationCommand()
}
