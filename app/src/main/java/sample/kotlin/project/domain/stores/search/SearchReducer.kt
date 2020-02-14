package sample.kotlin.project.domain.stores.search

import sample.kotlin.project.domain.core.mvi.Reducer

class SearchReducer : Reducer<SearchState, SearchAction> {

    override fun reduce(state: SearchState, action: SearchAction) =
        when (action) {

            is SearchAction.SearchClickAction, is SearchAction.SearchQueryChangeAction,
            is SearchAction.LoadSuggestionsAction -> state

            is SearchAction.SearchLoadingAction ->
                state.copy(
                    loading = true
                )

            is SearchAction.SearchSuccessAction ->
                state.copy(
                    loading = false,
                    data = action.data
                )

            is SearchAction.SearchFailureAction ->
                state.copy(
                    loading = false
                )

            is SearchAction.SuggestionsLoadedAction ->
                state.copy(
                    suggestions = action.suggestions
                )
        }
}
