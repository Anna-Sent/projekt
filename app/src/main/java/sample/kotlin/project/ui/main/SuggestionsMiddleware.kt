package sample.kotlin.project.ui.main

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers

class SuggestionsMiddleware : Middleware<Action, SearchState> {

    private val api = Api()
    private val uiScheduler: Scheduler = AndroidSchedulers.mainThread()

    override fun bind(
        actions: Observable<Action>,
        state: Observable<SearchState>
    ): Observable<Action> =
        actions.ofType<UserAction.SearchAction>(UserAction.SearchAction::class.java)
            .switchMap { action ->
                api.suggestions(action.query)
                    .toObservable()
                    .onErrorReturnItem(emptyList())
                    .map { result -> InternalAction.SuggectionsLoadedAction(result) }
                    .observeOn(uiScheduler)
            }
}
