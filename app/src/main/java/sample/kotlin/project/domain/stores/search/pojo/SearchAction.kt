package sample.kotlin.project.domain.stores.search.pojo

import sample.kotlin.project.domain.core.mvi.pojo.Action
import sample.kotlin.project.domain.pojo.search.Repository
import sample.kotlin.project.domain.repositories.search.SearchRequest

sealed class SearchAction : Action {

    data class OnSearchClick(val query: String) : SearchAction()

    object OnScrolledToBottom : SearchAction()

    object OnRefresh : SearchAction()

    object OnRetryNextPage : SearchAction()

    data class OnSearchQueryChanged(val query: String) : SearchAction()

    data class LoadSearchResults(
        val request: SearchRequest,
        val requestType: SearchRequestType
    ) : SearchAction()

    data class SearchLoadingSucceeded(
        val requestType: SearchRequestType,
        val repositories: List<Repository>
    ) : SearchAction()

    data class SearchLoadingFailed(
        val requestType: SearchRequestType,
        val error: Throwable
    ) : SearchAction()

    object LoadSuggestions : SearchAction()

    data class SuggestionsLoadingSucceeded(val suggestions: List<String>) : SearchAction()

    data class ConnectivityChanged(val isConnected: Boolean) : SearchAction()
}
