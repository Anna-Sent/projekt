package sample.kotlin.project.domain.stores.search.middlewares

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import sample.kotlin.project.domain.core.mvi.Middleware
import sample.kotlin.project.domain.sources.search.SearchRemoteSource
import sample.kotlin.project.domain.stores.search.SearchAction
import sample.kotlin.project.domain.stores.search.SearchEvent
import sample.kotlin.project.domain.stores.search.SearchState
import javax.inject.Inject

class SuggestionsMiddleware
@Inject constructor(
    private val searchRemoteSource: SearchRemoteSource
) : Middleware<SearchAction, SearchState, SearchEvent> {

    private val uiScheduler: Scheduler = AndroidSchedulers.mainThread()

    override fun bind(
        actions: Observable<SearchAction>,
        states: Observable<SearchState>,
        events: Consumer<SearchEvent>
    ): Observable<SearchAction> =
        actions
            .ofType<SearchAction.LoadSuggestionsAction>(SearchAction.LoadSuggestionsAction::class.java)
            .switchMap {
                searchRemoteSource.suggestions()
                    .toObservable()
                    .onErrorReturnItem(emptyList())
                    .map { SearchAction.SuggestionsLoadedAction(it) }
                    .observeOn(uiScheduler)
            }
}