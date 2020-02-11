package sample.kotlin.project.ui.main

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.withLatestFrom

class SearchMiddleware : Middleware<Action, SearchState> {

    private val api = Api()
    private val uiScheduler: Scheduler = AndroidSchedulers.mainThread()

    override fun bind(
        actions: Observable<Action>,
        state: Observable<SearchState>
    ): Observable<Action> =
        actions.ofType<UserAction.SearchAction>(UserAction.SearchAction::class.java)
            .withLatestFrom(state) { action, currentState -> action to currentState }
            .switchMap { (action, state) ->
                api.search(action.query)
                    .toObservable()
                    .map<InternalAction> { result -> InternalAction.SearchSuccessAction(result) }
                    .onErrorReturn { throwable -> InternalAction.SearchFailureAction(throwable) }
                    .observeOn(uiScheduler)
                    .startWith(InternalAction.SearchLoadingAction)
            }
}
