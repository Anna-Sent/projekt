package sample.kotlin.project.domain.stores.search.middlewares

import io.reactivex.Observable
import io.reactivex.functions.Consumer
import sample.kotlin.project.domain.core.mvi.BaseMiddleware
import sample.kotlin.project.domain.repositories.search.SearchRepository
import sample.kotlin.project.domain.stores.search.pojo.SearchAction
import sample.kotlin.project.domain.stores.search.pojo.SearchEvent
import sample.kotlin.project.domain.stores.search.pojo.SearchNavigationCommand
import sample.kotlin.project.domain.stores.search.pojo.SearchState
import javax.inject.Inject

class SearchMiddleware
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
            .ofType<SearchAction.LoadSearchResults>(
                SearchAction.LoadSearchResults::class.java
            )
            .distinctUntilChanged()
            .map(SearchAction.LoadSearchResults::request)
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
