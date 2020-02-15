package sample.kotlin.project.domain.stores.main.middlewares

import io.reactivex.Observable
import io.reactivex.functions.Consumer
import sample.kotlin.project.domain.core.mvi.Middleware
import sample.kotlin.project.domain.screens.SearchScreen
import sample.kotlin.project.domain.stores.main.MainAction
import sample.kotlin.project.domain.stores.main.MainEvent
import sample.kotlin.project.domain.stores.main.MainState
import sample.kotlin.project.presentation.app.AppRouter
import javax.inject.Inject

class NavigationMiddleware
@Inject constructor(
    private val router: AppRouter
) : Middleware<MainAction, MainState, MainEvent> {

    override fun bind(
        actions: Observable<MainAction>,
        states: Observable<MainState>,
        events: Consumer<MainEvent>
    ): Observable<MainAction> =
        actions
            .ofType<MainAction.NavigateToSearchAction>(MainAction.NavigateToSearchAction::class.java)
            .doOnNext { router.newRootScreen(SearchScreen()) }
            .switchMap { Observable.never<MainAction>() }
}
