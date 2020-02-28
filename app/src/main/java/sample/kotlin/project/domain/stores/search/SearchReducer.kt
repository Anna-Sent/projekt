package sample.kotlin.project.domain.stores.search

import sample.kotlin.project.domain.core.mvi.Reducer
import sample.kotlin.project.domain.pojo.search.RepositoryErrorItem
import sample.kotlin.project.domain.pojo.search.RepositoryProgressItem
import sample.kotlin.project.domain.stores.search.pojo.SearchAction
import sample.kotlin.project.domain.stores.search.pojo.SearchRequestType
import sample.kotlin.project.domain.stores.search.pojo.SearchState

internal class SearchReducer : Reducer<SearchState, SearchAction> {

    override fun reduce(action: SearchAction, state: SearchState) =
        when (action) {

            is SearchAction.OnSearchClick ->
                state.copy(lastQuery = action.query)

            is SearchAction.OnScrolledToBottom,
            is SearchAction.OnRefresh,
            is SearchAction.OnSearchQueryChanged
            -> state

            is SearchAction.LoadSearchResults ->
                when (action.requestType) {
                    SearchRequestType.FIRST_PAGE_INITIAL ->
                        state.copy(
                            requestType = action.requestType,
                            repositories = emptyList()
                        )
                    SearchRequestType.NEXT_PAGE ->
                        state.copy(
                            requestType = action.requestType,
                            repositories = (state.repositories.map { it.value }
                                    + RepositoryProgressItem)
                                .withIndex().toList()
                        )
                    SearchRequestType.FIRST_PAGE_REFRESH ->
                        state.copy(requestType = action.requestType)
                }

            is SearchAction.SearchLoadingSucceeded ->
                state.copy(
                    requestType = null,
                    lastLoadedPage = state.lastLoadedPage + 1,
                    repositories = (state.repositories.map { it.value }
                            - RepositoryProgressItem + action.repositories)
                        .withIndex().toList(),
                    failed = false
                )

            is SearchAction.SearchLoadingFailed ->
                when (action.requestType) {
                    SearchRequestType.FIRST_PAGE_INITIAL ->
                        state.copy(
                            requestType = null,
                            failed = true
                        )
                    SearchRequestType.NEXT_PAGE ->
                        state.copy(
                            requestType = null,
                            repositories = (state.repositories.map { it.value }
                                    + RepositoryErrorItem)
                                .withIndex().toList()
                        )
                    SearchRequestType.FIRST_PAGE_REFRESH ->
                        state.copy(requestType = null)
                }

            is SearchAction.LoadSuggestions -> state

            is SearchAction.SuggestionsLoadingSucceeded ->
                state.copy(suggestions = action.suggestions)

            is SearchAction.ConnectivityChanged ->
                state.copy(connected = action.isConnected)
        }
}
