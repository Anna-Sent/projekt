package sample.kotlin.project.domain.search

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import sample.kotlin.project.data.Api
import sample.kotlin.project.domain.mvi.Middleware

class SuggestionsMiddleware :
    Middleware<SearchAction, SearchState> {

    private val uiScheduler: Scheduler = AndroidSchedulers.mainThread()

    private val api = Api()

    override fun bind(
        actions: Observable<SearchAction>,
        states: Observable<SearchState>
    ): Observable<SearchAction> =
        actions
            .ofType<SearchAction.LoadSuggestionsAction>(SearchAction.LoadSuggestionsAction::class.java)
            .switchMap {
                api.suggestions()
                    .toObservable()
                    .onErrorReturnItem(emptyList())
                    .map { SearchAction.SuggestionsLoadedAction(it) }
                    .observeOn(uiScheduler)
            }
}
