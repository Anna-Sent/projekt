package sample.kotlin.project.domain.stores.search.pojo

import sample.kotlin.project.domain.core.mvi.pojo.Action
import sample.kotlin.project.domain.pojo.search.Repository
import sample.kotlin.project.domain.repositories.search.SearchRequest

sealed class SearchAction : Action {

    class OnSearchClick(val query: String) : SearchAction()

    class OnSearchQueryChanged(val query: String) : SearchAction()

    object LoadSuggestions : SearchAction()

    object OnScrolledToBottom : SearchAction()

    object SearchLoadingStarted : SearchAction()

    object SearchLoadingFinished : SearchAction()

    class SearchLoadingSucceeded(
        val request: SearchRequest,
        val repositories: List<Repository>
    ) : SearchAction()

    class SearchLoadingFailed(val error: Throwable) : SearchAction()

    class SuggestionsLoadingSucceeded(val suggestions: List<String>) : SearchAction()

    class ConnectivityChanged(val isConnected: Boolean) : SearchAction()
}
