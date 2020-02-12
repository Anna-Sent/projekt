package sample.kotlin.project.domain.search

import sample.kotlin.project.domain.mvi.Store
import javax.inject.Inject

class SearchStore @Inject constructor(initialState: SearchState) :
    Store<SearchAction, SearchState>(
        SearchReducer(),
        setOf(
            SearchMiddleware(),
            SuggestionsMiddleware()
        ),
        initialState
    )
