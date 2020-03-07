package sample.kotlin.project.domain.stores.search.middlewares

import io.reactivex.Observable
import io.reactivex.functions.Consumer
import sample.kotlin.project.domain.core.mvi.BaseMiddleware
import sample.kotlin.project.domain.repositories.search.SearchRepository
import sample.kotlin.project.domain.stores.search.pojo.SearchAction
import sample.kotlin.project.domain.stores.search.pojo.SearchAction.LoadSearchResults
import sample.kotlin.project.domain.stores.search.pojo.SearchAction.SearchLoadingFailed
import sample.kotlin.project.domain.stores.search.pojo.SearchAction.SearchLoadingSucceeded
import sample.kotlin.project.domain.stores.search.pojo.SearchEvent
import sample.kotlin.project.domain.stores.search.pojo.SearchEvent.SearchRefreshFailureEvent
import sample.kotlin.project.domain.stores.search.pojo.SearchNavigationCommand
import sample.kotlin.project.domain.stores.search.pojo.SearchRequestType.FIRST_PAGE_REFRESH
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
            .ofType(LoadSearchResults::class.java)
            .switchMap { action ->
                searchRepository.search(action.request)
                    .toObservable()
                    .map<SearchAction> {
                        SearchLoadingSucceeded(
                            action.requestType,
                            action.request.page,
                            it.nextPage,
                            it.lastPage,
                            it.repositories.items
                        )
                    }
                    .doOnError {
                        if (action.requestType == FIRST_PAGE_REFRESH) {
                            events.accept(SearchRefreshFailureEvent(it))
                        }
                    }
                    .onErrorReturn { SearchLoadingFailed(action.requestType, it) }
            }
}
