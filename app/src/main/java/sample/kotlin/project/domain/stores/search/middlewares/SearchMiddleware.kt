package sample.kotlin.project.domain.stores.search.middlewares

import io.reactivex.Observable
import io.reactivex.functions.Consumer
import sample.kotlin.project.domain.core.mvi.Middleware
import sample.kotlin.project.domain.sources.common.RequestStatusSource
import sample.kotlin.project.domain.sources.common.RequestType
import sample.kotlin.project.domain.sources.search.SearchSource
import sample.kotlin.project.domain.stores.search.entities.SearchAction
import sample.kotlin.project.domain.stores.search.entities.SearchEvent
import sample.kotlin.project.domain.stores.search.entities.SearchNavigationCommand
import sample.kotlin.project.domain.stores.search.entities.SearchState
import javax.inject.Inject

class SearchMiddleware
@Inject constructor(
    private val searchSource: SearchSource,
    private val requestStatusSource: RequestStatusSource
) : Middleware<SearchState, SearchAction, SearchEvent, SearchNavigationCommand> {

    override fun bind(
        states: Observable<SearchState>,
        actions: Observable<SearchAction>,
        events: Consumer<SearchEvent>,
        navigationCommands: Consumer<SearchNavigationCommand>
    ): Observable<SearchAction> =
        actions
            .ofType<SearchAction.SearchClickAction>(
                SearchAction.SearchClickAction::class.java
            )
            .switchMap { action ->
                searchSource.search(action.query)
                    .compose(requestStatusSource.applyStatusUpdating(RequestType.Search))
                    .toObservable()
                    .map<SearchAction> { SearchAction.SearchSuccessAction(it) }
                    .doOnError { events.accept(SearchEvent.SearchFailureEvent(it)) }
                    .onErrorReturn { SearchAction.SearchFailureAction(it) }
            }
}
