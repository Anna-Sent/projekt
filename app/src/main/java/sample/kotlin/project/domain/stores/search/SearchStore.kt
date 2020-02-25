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
    searchNextPageMiddleware: SearchNextPageMiddleware,
    stateMiddleware: StateMiddleware,
    suggestionsMiddleware: SuggestionsMiddleware,
    connectivityMiddleware: ConnectivityMiddleware,
    initialState: SearchState
) : Store<SearchState, SearchAction, SearchEvent, SearchNavigationCommand>(
    schedulersProvider,
    SearchReducer(),
    setOf(
        searchMiddleware,
        searchNextPageMiddleware,
        stateMiddleware,
        suggestionsMiddleware,
        connectivityMiddleware
    ),
    initialState
)
