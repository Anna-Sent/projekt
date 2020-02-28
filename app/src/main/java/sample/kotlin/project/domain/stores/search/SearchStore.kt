package sample.kotlin.project.domain.stores.search

import sample.kotlin.project.domain.core.mvi.Store
import sample.kotlin.project.domain.providers.schedulers.SchedulersProvider
import sample.kotlin.project.domain.stores.search.middlewares.*
import sample.kotlin.project.domain.stores.search.pojo.SearchAction
import sample.kotlin.project.domain.stores.search.pojo.SearchEvent
import sample.kotlin.project.domain.stores.search.pojo.SearchNavigationCommand
import sample.kotlin.project.domain.stores.search.pojo.SearchState
import javax.inject.Inject

class SearchStore
@Inject constructor(
    schedulersProvider: SchedulersProvider,
    searchMiddleware: SearchMiddleware,
    searchFirstPageRefreshMiddleware: SearchFirstPageRefreshMiddleware,
    searchFirstPageRetryMiddleware: SearchFirstPageRetryMiddleware,
    searchFirstPageMiddleware: SearchFirstPageMiddleware,
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
        searchFirstPageRefreshMiddleware,
        searchFirstPageRetryMiddleware,
        searchFirstPageMiddleware,
        searchNextPageMiddleware,
        searchNextPageRetryMiddleware,
        suggestionsMiddleware,
        connectivityMiddleware
    ),
    initialState
)
