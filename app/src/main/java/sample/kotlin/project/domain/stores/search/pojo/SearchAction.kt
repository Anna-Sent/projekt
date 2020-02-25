package sample.kotlin.project.domain.stores.search.pojo

import sample.kotlin.project.domain.core.mvi.pojo.Action
import sample.kotlin.project.domain.pojo.search.Repository

sealed class SearchAction : Action {

    class OnSearchClick(val query: String) : SearchAction()

    class OnSearchQueryChanged(val query: String) : SearchAction()

    object OnActivityCreatedFirstTime : SearchAction()

    object SearchLoadingStarted : SearchAction()

    object SearchLoadingFinished : SearchAction()

    class SearchLoadingSucceeded(val repositories: List<Repository>) : SearchAction()

    class SearchLoadingFailed(val error: Throwable) : SearchAction()

    class SuggestionsLoadingSucceeded(val suggestions: List<String>) : SearchAction()

    class ConnectivityChanged(val isConnected: Boolean) : SearchAction()
}
