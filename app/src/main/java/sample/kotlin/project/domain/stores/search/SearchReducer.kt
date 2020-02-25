package sample.kotlin.project.domain.stores.search

import sample.kotlin.project.domain.core.mvi.Reducer
import sample.kotlin.project.domain.stores.search.pojo.SearchAction
import sample.kotlin.project.domain.stores.search.pojo.SearchState
import java.util.*

internal class SearchReducer : Reducer<SearchState, SearchAction> {

    override fun reduce(state: SearchState, action: SearchAction) =
        when (action) {

            is SearchAction.OnSearchClick ->
                state.copy(lastQuery = action.query)

            is SearchAction.OnSearchQueryChanged,
            is SearchAction.LoadSuggestions,
            is SearchAction.OnScrolledToBottom
            -> state

            is SearchAction.SearchLoadingStarted ->
                state.copy(
                    loading = true
                )

            is SearchAction.SearchLoadingFinished ->
                state.copy(
                    loading = false
                )

            is SearchAction.SearchLoadingSucceeded ->
                if (action.request.page == state.lastLoadedPage + 1)
                    state.copy(
                        lastLoadedPage = action.request.page,
                        repositories = state.repositories + action.repositories
                    )
                else state

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

    private fun <T> List<T>.plus(other: List<T>): List<T> {
        val result = ArrayList<T>()
        result.addAll(this)
        result.addAll(other)
        return Collections.unmodifiableList(result)
    }
}
