package sample.kotlin.project.domain.stores.search

import sample.kotlin.project.domain.core.mvi.Store
import sample.kotlin.project.domain.stores.search.middlewares.SearchMiddleware
import sample.kotlin.project.domain.stores.search.middlewares.StateMiddleware
import sample.kotlin.project.domain.stores.search.middlewares.SuggestionsMiddleware
import javax.inject.Inject

class SearchStore
@Inject constructor(
    searchMiddleware: SearchMiddleware,
    stateMiddleware: StateMiddleware,
    suggestionsMiddleware: SuggestionsMiddleware,
    initialState: SearchState
) : Store<SearchAction, SearchState, SearchEvent>(
    SearchReducer(),
    setOf(
        searchMiddleware,
        stateMiddleware,
        suggestionsMiddleware
    ),
    initialState
)
