package sample.kotlin.project.presentation.fragments.splash

import sample.kotlin.project.domain.stores.splash.SplashStore
import sample.kotlin.project.domain.stores.splash.entities.SplashAction
import sample.kotlin.project.domain.stores.splash.entities.SplashEvent
import sample.kotlin.project.domain.stores.splash.entities.SplashNavigationCommand
import sample.kotlin.project.domain.stores.splash.entities.SplashState
import sample.kotlin.project.presentation.app.AppRouter
import sample.kotlin.project.presentation.core.views.BaseViewModel
import sample.kotlin.project.presentation.screens.SearchScreen
import javax.inject.Inject

class SplashViewModel
@Inject constructor(
    store: SplashStore,
    private val router: AppRouter
) : BaseViewModel<SplashState, SplashAction, SplashEvent, SplashNavigationCommand>(store) {

    override fun handleNavigationCommand(navigationCommand: SplashNavigationCommand) {
        when (navigationCommand) {

            is SplashNavigationCommand.NavigateToSearchScreen -> router.newRootScreen(SearchScreen())
        }
    }
}
