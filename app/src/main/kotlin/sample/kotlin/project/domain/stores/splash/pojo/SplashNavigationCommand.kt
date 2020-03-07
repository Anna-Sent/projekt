package sample.kotlin.project.domain.stores.splash.pojo

import sample.kotlin.project.domain.core.mvi.pojo.NavigationCommand

sealed class SplashNavigationCommand : NavigationCommand {

    object NavigateToSearchScreen : SplashNavigationCommand()
}
