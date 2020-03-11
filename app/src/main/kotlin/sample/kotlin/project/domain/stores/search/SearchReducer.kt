package sample.kotlin.project.domain.stores.search

import sample.kotlin.project.domain.core.lists.Item
import sample.kotlin.project.domain.core.mvi.Reducer
import sample.kotlin.project.domain.pojo.search.Repository
import sample.kotlin.project.domain.pojo.search.RepositoryErrorItem
import sample.kotlin.project.domain.pojo.search.RepositoryProgressItem
import sample.kotlin.project.domain.stores.search.pojo.SearchAction
import sample.kotlin.project.domain.stores.search.pojo.SearchAction.ConnectivityChanged
import sample.kotlin.project.domain.stores.search.pojo.SearchAction.LoadSearchResults
import sample.kotlin.project.domain.stores.search.pojo.SearchAction.OnLoadSuggestions
import sample.kotlin.project.domain.stores.search.pojo.SearchAction.OnRefresh
import sample.kotlin.project.domain.stores.search.pojo.SearchAction.OnRetryClick
import sample.kotlin.project.domain.stores.search.pojo.SearchAction.OnRetryNextPageClick
import sample.kotlin.project.domain.stores.search.pojo.SearchAction.OnScrolledToBottom
import sample.kotlin.project.domain.stores.search.pojo.SearchAction.OnSearchClick
import sample.kotlin.project.domain.stores.search.pojo.SearchAction.OnSearchQueryChanged
import sample.kotlin.project.domain.stores.search.pojo.SearchAction.SearchLoadingFailed
import sample.kotlin.project.domain.stores.search.pojo.SearchAction.SearchLoadingSucceeded
import sample.kotlin.project.domain.stores.search.pojo.SearchAction.SuggestionsLoadingSucceeded
import sample.kotlin.project.domain.stores.search.pojo.SearchRequestType.FIRST_PAGE
import sample.kotlin.project.domain.stores.search.pojo.SearchRequestType.FIRST_PAGE_REFRESH
import sample.kotlin.project.domain.stores.search.pojo.SearchRequestType.FIRST_PAGE_RETRY
import sample.kotlin.project.domain.stores.search.pojo.SearchRequestType.NEXT_PAGE
import sample.kotlin.project.domain.stores.search.pojo.SearchState

internal class SearchReducer : Reducer<SearchState, SearchAction> {

    @Suppress("LongMethod")
    override fun reduce(action: SearchAction, state: SearchState) =
        when (action) {

            is OnSearchClick -> state.copy(lastQuery = action.query)

            is OnScrolledToBottom,
            is OnRefresh,
            is OnRetryClick,
            is OnRetryNextPageClick,
            is OnSearchQueryChanged,
            is OnLoadSuggestions
            -> state

            is ConnectivityChanged -> state.copy(connected = action.connected)

            is LoadSearchResults -> progressState(action, state)

            is SearchLoadingSucceeded -> succeededState(action, state)

            is SearchLoadingFailed -> failedState(action, state)

            is SuggestionsLoadingSucceeded -> state.copy(suggestions = action.suggestions)
        }

    private fun progressState(action: LoadSearchResults, state: SearchState) =
        when (action.requestType) {
            FIRST_PAGE ->
                state.copy(
                    requestType = action.requestType,
                    repositories = emptyList()
                )
            NEXT_PAGE ->
                state.copy(
                    requestType = action.requestType,
                    repositories = state.repositories.map { it.value }
                        .removeError()
                        .addProgress()
                        .withIndex().toList()
                )
            FIRST_PAGE_REFRESH, FIRST_PAGE_RETRY ->
                state.copy(requestType = action.requestType)
        }

    private fun succeededState(action: SearchLoadingSucceeded, state: SearchState) =
        state.copy(
            requestType = null,
            lastLoadedPage = action.loadedPage,
            nextPage = action.nextPage,
            lastPage = action.lastPage,
            repositories = newItems(action, state),
            error = null
        )

    private fun newItems(action: SearchLoadingSucceeded, state: SearchState) =
        when (action.requestType) {
            FIRST_PAGE, FIRST_PAGE_RETRY, FIRST_PAGE_REFRESH ->
                action.repositories
                    .withIndex().toList()
            NEXT_PAGE ->
                (state.repositories.map { it.value }.removeProgress() + action.repositories)
                    .withIndex().toList()
        }

    private fun failedState(action: SearchLoadingFailed, state: SearchState) =
        when (action.requestType) {
            FIRST_PAGE, FIRST_PAGE_RETRY ->
                state.copy(
                    requestType = null,
                    error = action.error
                )
            NEXT_PAGE ->
                state.copy(
                    requestType = null,
                    repositories = state.repositories.map { it.value }
                        .removeProgress()
                        .addError(action.error)
                        .withIndex().toList()
                )
            FIRST_PAGE_REFRESH ->
                state.copy(requestType = null)
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
