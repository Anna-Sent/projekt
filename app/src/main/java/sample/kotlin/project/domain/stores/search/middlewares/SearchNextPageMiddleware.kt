package sample.kotlin.project.domain.stores.search.middlewares

import io.reactivex.Observable
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.withLatestFrom
import sample.kotlin.project.domain.core.mvi.BaseMiddleware
import sample.kotlin.project.domain.repositories.search.SearchRepository
import sample.kotlin.project.domain.repositories.search.SearchRequest
import sample.kotlin.project.domain.stores.search.pojo.SearchAction
import sample.kotlin.project.domain.stores.search.pojo.SearchEvent
import sample.kotlin.project.domain.stores.search.pojo.SearchNavigationCommand
import sample.kotlin.project.domain.stores.search.pojo.SearchState
import javax.inject.Inject

class SearchNextPageMiddleware
@Inject constructor(
    private val searchRepository: SearchRepository
) : BaseMiddleware<SearchState, SearchAction, SearchEvent, SearchNavigationCommand>() {

    override fun bind(
        states: Observable<SearchState>,
        actions: Observable<SearchAction>,
        events: Consumer<SearchEvent>,
        navigationCommands: Consumer<SearchNavigationCommand>
    ): Observable<SearchAction> =
        actions
            .ofType<SearchAction.OnScrolledToBottom>(
                SearchAction.OnScrolledToBottom::class.java
            )
            .withLatestFrom(states) { _, state ->
                SearchRequest(state.lastQuery, state.lastLoadedPage + 1)
            }
            .distinctUntilChanged()
            .switchMap { query ->
                searchRepository.search(query)
                    .toObservable()
                    .map<SearchAction> {
                        SearchAction.SearchLoadingSucceeded(
                            it.request,
                            it.repositories.items
                        )
                    }
                    .doOnError { events.accept(SearchEvent.SearchFailureEvent(it)) }
                    .onErrorReturn { SearchAction.SearchLoadingFailed(it) }
            }
}
