package sample.kotlin.project.presentation.fragments.splash

import sample.kotlin.project.domain.stores.splash.SplashStore
import sample.kotlin.project.domain.stores.splash.pojo.SplashAction
import sample.kotlin.project.domain.stores.splash.pojo.SplashEvent
import sample.kotlin.project.domain.stores.splash.pojo.SplashNavigationCommand
import sample.kotlin.project.domain.stores.splash.pojo.SplashNavigationCommand.NavigateToSearchScreen
import sample.kotlin.project.domain.stores.splash.pojo.SplashState
import sample.kotlin.project.presentation.app.AppRouter
import sample.kotlin.project.presentation.core.views.BaseViewModel
import sample.kotlin.project.presentation.screens.SearchScreen
import javax.inject.Inject

class SplashViewModel
@Inject constructor(
    store: SplashStore,
    private val router: AppRouter
) : BaseViewModel<SplashState, SplashAction, SplashEvent, SplashNavigationCommand>(store) {

    override fun handle(navigationCommand: SplashNavigationCommand) {
        when (navigationCommand) {

            is NavigateToSearchScreen -> router.newRootScreen(SearchScreen())
        }
    }
}
