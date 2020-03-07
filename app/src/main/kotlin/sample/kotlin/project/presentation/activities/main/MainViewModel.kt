package sample.kotlin.project.presentation.activities.main

import sample.kotlin.project.domain.stores.main.MainStore
import sample.kotlin.project.domain.stores.main.pojo.MainAction
import sample.kotlin.project.domain.stores.main.pojo.MainEvent
import sample.kotlin.project.domain.stores.main.pojo.MainNavigationCommand
import sample.kotlin.project.domain.stores.main.pojo.MainNavigationCommand.NavigateToSplashScreen
import sample.kotlin.project.domain.stores.main.pojo.MainState
import sample.kotlin.project.presentation.app.AppRouter
import sample.kotlin.project.presentation.core.views.BaseViewModel
import sample.kotlin.project.presentation.screens.SplashScreen
import javax.inject.Inject

class MainViewModel
@Inject constructor(
    store: MainStore,
    private val router: AppRouter
) : BaseViewModel<MainState, MainAction, MainEvent, MainNavigationCommand>(store) {

    override fun handle(navigationCommand: MainNavigationCommand) {
        when (navigationCommand) {

            is NavigateToSplashScreen -> router.newRootScreen(SplashScreen())
        }
    }
}
