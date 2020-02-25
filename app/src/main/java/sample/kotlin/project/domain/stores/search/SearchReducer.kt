package sample.kotlin.project.domain.stores.search

import sample.kotlin.project.domain.core.mvi.Reducer
import sample.kotlin.project.domain.stores.search.pojo.SearchAction
import sample.kotlin.project.domain.stores.search.pojo.SearchState

internal class SearchReducer : Reducer<SearchState, SearchAction> {

    override fun reduce(state: SearchState, action: SearchAction) =
        when (action) {

            is SearchAction.OnSearchClick, is SearchAction.OnSearchQueryChanged,
            is SearchAction.OnActivityCreatedFirstTime -> state

            is SearchAction.SearchLoadingStarted ->
                state.copy(
                    loading = true
                )

            is SearchAction.SearchLoadingFinished ->
                state.copy(
                    loading = false
                )

            is SearchAction.SearchLoadingSucceeded ->
                state.copy(
                    repositories = action.repositories
                )

            is SearchAction.SearchLoadingFailed -> state

            is SearchAction.SuggestionsLoadingSucceeded ->
                state.copy(
                    suggestions = action.suggestions
                )

            is SearchAction.ConnectivityChanged ->
                state.copy(
                    connected = action.isConnected
                )
        }
}
