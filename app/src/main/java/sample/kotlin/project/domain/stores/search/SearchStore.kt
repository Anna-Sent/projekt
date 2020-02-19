package sample.kotlin.project.domain.stores.search

import io.reactivex.Scheduler
import sample.kotlin.project.domain.core.mvi.Store
import sample.kotlin.project.domain.stores.search.entities.SearchAction
import sample.kotlin.project.domain.stores.search.entities.SearchEvent
import sample.kotlin.project.domain.stores.search.entities.SearchNavigationCommand
import sample.kotlin.project.domain.stores.search.entities.SearchState
import sample.kotlin.project.domain.stores.search.middlewares.ConnectivityMiddleware
import sample.kotlin.project.domain.stores.search.middlewares.SearchMiddleware
import sample.kotlin.project.domain.stores.search.middlewares.StateMiddleware
import sample.kotlin.project.domain.stores.search.middlewares.SuggestionsMiddleware
import javax.inject.Inject

class SearchStore
@Inject constructor(
    uiScheduler: Scheduler,
    searchMiddleware: SearchMiddleware,
    stateMiddleware: StateMiddleware,
    suggestionsMiddleware: SuggestionsMiddleware,
    connectivityMiddleware: ConnectivityMiddleware,
    initialState: SearchState
) : Store<SearchState, SearchAction, SearchEvent, SearchNavigationCommand>(
    uiScheduler,
    SearchReducer(),
    setOf(
        searchMiddleware,
        stateMiddleware,
        suggestionsMiddleware,
        connectivityMiddleware
    ),
    initialState
)
