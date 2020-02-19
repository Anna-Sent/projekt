package sample.kotlin.project.domain.stores.main.entities

import sample.kotlin.project.domain.core.mvi.entities.NavigationCommand

sealed class MainNavigationCommand : NavigationCommand {

    object NavigateToSplashScreen : MainNavigationCommand()
}
