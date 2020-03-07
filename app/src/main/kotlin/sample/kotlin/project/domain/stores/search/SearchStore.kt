package sample.kotlin.project.domain.stores.search

import sample.kotlin.project.domain.core.mvi.Store
import sample.kotlin.project.domain.providers.schedulers.SchedulersProvider
import sample.kotlin.project.domain.stores.search.middlewares.ConnectivityMiddleware
import sample.kotlin.project.domain.stores.search.middlewares.SearchFirstPageMiddleware
import sample.kotlin.project.domain.stores.search.middlewares.SearchFirstPageRefreshMiddleware
import sample.kotlin.project.domain.stores.search.middlewares.SearchFirstPageRetryMiddleware
import sample.kotlin.project.domain.stores.search.middlewares.SearchMiddleware
import sample.kotlin.project.domain.stores.search.middlewares.SearchNextPageMiddleware
import sample.kotlin.project.domain.stores.search.middlewares.SearchNextPageRetryMiddleware
import sample.kotlin.project.domain.stores.search.middlewares.SuggestionsMiddleware
import sample.kotlin.project.domain.stores.search.pojo.SearchAction
import sample.kotlin.project.domain.stores.search.pojo.SearchAction.OnSearchClick
import sample.kotlin.project.domain.stores.search.pojo.SearchEvent
import sample.kotlin.project.domain.stores.search.pojo.SearchNavigationCommand
import sample.kotlin.project.domain.stores.search.pojo.SearchState
import javax.inject.Inject

class SearchStore
@Inject constructor(
    schedulersProvider: SchedulersProvider,
    searchMiddleware: SearchMiddleware,
    searchFirstPageMiddleware: SearchFirstPageMiddleware,
    searchFirstPageRefreshMiddleware: SearchFirstPageRefreshMiddleware,
    searchFirstPageRetryMiddleware: SearchFirstPageRetryMiddleware,
    searchNextPageMiddleware: SearchNextPageMiddleware,
    searchNextPageRetryMiddleware: SearchNextPageRetryMiddleware,
    suggestionsMiddleware: SuggestionsMiddleware,
    connectivityMiddleware: ConnectivityMiddleware,
    initialState: SearchState
) : Store<SearchState, SearchAction, SearchEvent, SearchNavigationCommand>(
    schedulersProvider,
    SearchReducer(),
    setOf(
        searchMiddleware,
        searchFirstPageMiddleware,
        searchFirstPageRefreshMiddleware,
        searchFirstPageRetryMiddleware,
        searchNextPageMiddleware,
        searchNextPageRetryMiddleware,
        suggestionsMiddleware,
        connectivityMiddleware
    ),
    initialState
) {
    init {
        if (initialState != SearchState() && initialState.lastQuery != null) {
            // restored and has query
            dispatch(OnSearchClick(initialState.lastQuery))
        }
    }
}
