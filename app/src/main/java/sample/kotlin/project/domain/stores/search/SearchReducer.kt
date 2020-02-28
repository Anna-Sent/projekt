package sample.kotlin.project.domain.stores.search

import sample.kotlin.project.domain.core.lists.Item
import sample.kotlin.project.domain.core.mvi.Reducer
import sample.kotlin.project.domain.pojo.search.Repository
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
            is SearchAction.OnRetryClick,
            is SearchAction.OnRetryNextPageClick,
            is SearchAction.OnSearchQueryChanged
            -> state

            is SearchAction.LoadSearchResults ->
                when (action.requestType) {
                    SearchRequestType.FIRST_PAGE ->
                        state.copy(
                            requestType = action.requestType,
                            repositories = emptyList()
                        )
                    SearchRequestType.NEXT_PAGE ->
                        state.copy(
                            requestType = action.requestType,
                            repositories = state.repositories.map { it.value }
                                .removeError()
                                .addProgress()
                                .withIndex().toList()
                        )
                    SearchRequestType.FIRST_PAGE_REFRESH, SearchRequestType.FIRST_PAGE_RETRY ->
                        state.copy(requestType = action.requestType)
                }

            is SearchAction.SearchLoadingSucceeded ->
                state.copy(
                    requestType = null,
                    lastLoadedPage = state.lastLoadedPage + 1,
                    repositories = (state.repositories.map { it.value }
                        .removeProgress() + action.repositories)
                        .withIndex().toList(),
                    error = null
                )

            is SearchAction.SearchLoadingFailed ->
                when (action.requestType) {
                    SearchRequestType.FIRST_PAGE, SearchRequestType.FIRST_PAGE_RETRY ->
                        state.copy(
                            requestType = null,
                            error = action.error
                        )
                    SearchRequestType.NEXT_PAGE ->
                        state.copy(
                            requestType = null,
                            repositories = state.repositories.map { it.value }
                                .removeProgress()
                                .addError(action.error)
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

    companion object {
        private fun List<Item<Repository>>.addProgress() =
            this + RepositoryProgressItem

        private fun List<Item<Repository>>.removeProgress() =
            when (true) {
                isEmpty() -> this
                last() is RepositoryProgressItem -> subList(0, lastIndex)
                else -> this
            }

        private fun List<Item<Repository>>.addError(error: Throwable) =
            this + RepositoryErrorItem(error)

        private fun List<Item<Repository>>.removeError() =
            when (true) {
                isEmpty() -> this
                last() is RepositoryErrorItem -> subList(0, lastIndex)
                else -> this
            }
    }
}
