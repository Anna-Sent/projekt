package sample.kotlin.project.domain.stores.main.middlewares

import io.reactivex.Observable
import io.reactivex.functions.Consumer
import sample.kotlin.project.domain.core.mvi.Middleware
import sample.kotlin.project.presentation.screens.SearchScreen
import sample.kotlin.project.domain.stores.main.entities.MainAction
import sample.kotlin.project.domain.stores.main.entities.MainEvent
import sample.kotlin.project.domain.stores.main.entities.MainNavigationCommand
import sample.kotlin.project.domain.stores.main.entities.MainState
import sample.kotlin.project.presentation.app.AppRouter
import javax.inject.Inject

class NavigationMiddleware
@Inject constructor(
    private val router: AppRouter
) : Middleware<MainState, MainAction, MainEvent, MainNavigationCommand> {

    override fun bind(
        states: Observable<MainState>,
        actions: Observable<MainAction>,
        events: Consumer<MainEvent>,
        navigationCommands: Consumer<MainNavigationCommand>
    ): Observable<MainAction> =
        actions
            .ofType<MainAction.NavigateToSearchAction>(
                MainAction.NavigateToSearchAction::class.java
            )
            .doOnNext { router.newRootScreen(SearchScreen()) }
            .switchMap { Observable.never<MainAction>() }
}
