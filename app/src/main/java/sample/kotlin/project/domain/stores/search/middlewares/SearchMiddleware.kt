package sample.kotlin.project.domain.stores.search.middlewares

import io.reactivex.Observable
import io.reactivex.functions.Consumer
import sample.kotlin.project.domain.core.mvi.Middleware
import sample.kotlin.project.domain.sources.common.RequestStatusLocalSource
import sample.kotlin.project.domain.sources.common.RequestType
import sample.kotlin.project.domain.sources.search.SearchRemoteSource
import sample.kotlin.project.domain.stores.search.data.SearchAction
import sample.kotlin.project.domain.stores.search.data.SearchEvent
import sample.kotlin.project.domain.stores.search.data.SearchState
import javax.inject.Inject

class SearchMiddleware
@Inject constructor(
    private val searchRemoteSource: SearchRemoteSource,
    private val requestStatusLocalSource: RequestStatusLocalSource
) : Middleware<SearchAction, SearchState, SearchEvent> {

    override fun bind(
        actions: Observable<SearchAction>,
        states: Observable<SearchState>,
        events: Consumer<SearchEvent>
    ): Observable<SearchAction> =
        actions
            .ofType<SearchAction.SearchClickAction>(
                SearchAction.SearchClickAction::class.java
            )
            .switchMap { action ->
                searchRemoteSource.search(action.query)
                    .compose(requestStatusLocalSource.applyStatusUpdating(RequestType.Search))
                    .toObservable()
                    .map<SearchAction> { SearchAction.SearchSuccessAction(it) }
                    .doOnError { events.accept(SearchEvent.SearchFailureEvent(it)) }
                    .onErrorReturn { SearchAction.SearchFailureAction(it) }
            }
}
