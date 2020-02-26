package sample.kotlin.project.domain.stores.search

import sample.kotlin.project.domain.core.mvi.Reducer
import sample.kotlin.project.domain.pojo.search.RepositoryProgress
import sample.kotlin.project.domain.stores.search.pojo.LoadingStatus
import sample.kotlin.project.domain.stores.search.pojo.SearchAction
import sample.kotlin.project.domain.stores.search.pojo.SearchState

internal class SearchReducer : Reducer<SearchState, SearchAction> {

    override fun reduce(state: SearchState, action: SearchAction) =
        when (action) {

            is SearchAction.OnSearchClick ->
                state.copy(lastQuery = action.query)

            is SearchAction.OnScrolledToBottom,
            is SearchAction.OnRefresh,
            is SearchAction.OnSearchQueryChanged
            -> state

            is SearchAction.LoadSearchResults -> {
                val newState = state.copy(loadingStatus = action.loadingStatus)
                when (true) {
                    action.loadingStatus == LoadingStatus.FIRST_PAGE_INITIAL ->
                        newState.copy(repositories = emptyList())
                    action.loadingStatus == LoadingStatus.NEXT_PAGE ->
                        newState.copy(
                            repositories = (state.repositories.map { it.value }
                                    + RepositoryProgress)
                                .withIndex().toList()
                        )
                    action.loadingStatus == LoadingStatus.FIRST_PAGE_REFRESH ->
                        newState
                    else -> state
                }
            }

            is SearchAction.SearchLoadingSucceeded ->
                when (true) {
                    action.request.page == 1 ->
                        state.copy(
                            loadingStatus = null,
                            lastLoadedPage = action.request.page,
                            repositories = action.repositories.withIndex().toList()
                        )
                    action.request.page == state.lastLoadedPage + 1 ->
                        state.copy(
                            loadingStatus = null,
                            lastLoadedPage = action.request.page,
                            repositories = (state.repositories.map { it.value }
                                    - RepositoryProgress + action.repositories)
                                .withIndex().toList()
                        )
                    else -> state
                }

            is SearchAction.SearchLoadingFailed ->
                state.copy(loadingStatus = null)

            is SearchAction.LoadSuggestions -> state

            is SearchAction.SuggestionsLoadingSucceeded ->
                state.copy(suggestions = action.suggestions)

            is SearchAction.ConnectivityChanged ->
                state.copy(connected = action.isConnected)
        }
}
