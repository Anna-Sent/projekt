package sample.kotlin.project.domain.stores.search.pojo

import sample.kotlin.project.domain.core.mvi.pojo.Action

sealed class SearchAction : Action {

    class SearchClickAction(val query: String) : SearchAction()

    class SearchQueryChangeAction(val query: String) : SearchAction()

    object LoadSuggestionsAction : SearchAction()

    object SearchLoadingStartedAction : SearchAction()

    object SearchLoadingFinishedAction : SearchAction()

    class SearchSuccessAction(val data: String) : SearchAction()

    class SearchFailureAction(val error: Throwable) : SearchAction()

    class SuggestionsLoadedAction(val suggestions: List<String>) : SearchAction()

    class NetworkConnectedAction(val isConnected: Boolean) : SearchAction()
}
