package sample.kotlin.project.domain.stores.search

import sample.kotlin.project.domain.core.mvi.Reducer
import sample.kotlin.project.domain.stores.search.pojo.SearchAction
import sample.kotlin.project.domain.stores.search.pojo.SearchState

internal class SearchReducer : Reducer<SearchState, SearchAction> {

    override fun reduce(state: SearchState, action: SearchAction) =
        when (action) {

            is SearchAction.SearchClickAction, is SearchAction.SearchQueryChangeAction,
            is SearchAction.LoadSuggestionsAction -> state

            is SearchAction.SearchLoadingStartedAction ->
                state.copy(
                    loading = true
                )

            is SearchAction.SearchLoadingFinishedAction ->
                state.copy(
                    loading = false
                )

            is SearchAction.SearchSuccessAction ->
                state.copy(
                    repositories = action.repositories
                )

            is SearchAction.SearchFailureAction -> state

            is SearchAction.SuggestionsLoadedAction ->
                state.copy(
                    suggestions = action.suggestions
                )

            is SearchAction.NetworkConnectedAction ->
                state.copy(
                    connected = action.isConnected
                )
        }
}
