package sample.kotlin.project.presentation.activities.main

import sample.kotlin.project.domain.stores.main.MainStore
import sample.kotlin.project.domain.stores.main.entities.MainAction
import sample.kotlin.project.domain.stores.main.entities.MainEvent
import sample.kotlin.project.domain.stores.main.entities.MainNavigationCommand
import sample.kotlin.project.domain.stores.main.entities.MainState
import sample.kotlin.project.presentation.app.AppRouter
import sample.kotlin.project.presentation.core.BaseViewModel
import sample.kotlin.project.presentation.screens.SplashScreen
import javax.inject.Inject

class MainViewModel
@Inject constructor(
    store: MainStore,
    private val router: AppRouter
) : BaseViewModel<MainState, MainAction, MainEvent, MainNavigationCommand>(store) {

    override fun handleNavigationCommand(navigationCommand: MainNavigationCommand) {
        when (navigationCommand) {

            is MainNavigationCommand.NavigateToSplashScreen -> router.newRootScreen(SplashScreen())
        }
    }
}
