package sample.kotlin.project.domain.stores.search.middlewares

import io.reactivex.Observable
import io.reactivex.functions.Consumer
import sample.kotlin.project.domain.core.mvi.BaseMiddleware
import sample.kotlin.project.domain.repositories.search.SearchRepository
import sample.kotlin.project.domain.stores.search.pojo.*
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
            .switchMap { action ->
                searchRepository.search(action.request)
                    .toObservable()
                    .map<SearchAction> {
                        SearchAction.SearchLoadingSucceeded(
                            action.requestType,
                            it.repositories.items
                        )
                    }
                    .doOnError {
                        if (action.requestType == SearchRequestType.FIRST_PAGE_REFRESH) {
                            events.accept(SearchEvent.SearchRefreshFailureEvent(it))
                        }
                    }
                    .onErrorReturn { SearchAction.SearchLoadingFailed(action.requestType, it) }
            }
}
