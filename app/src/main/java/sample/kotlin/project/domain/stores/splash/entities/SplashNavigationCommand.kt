package sample.kotlin.project.domain.stores.splash.entities

import sample.kotlin.project.domain.core.mvi.entities.NavigationCommand

sealed class SplashNavigationCommand : NavigationCommand {

    object NavigateToSearchScreen : SplashNavigationCommand()
}
