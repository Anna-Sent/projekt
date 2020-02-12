package sample.kotlin.project.domain.search

import sample.kotlin.project.domain.mvi.Action

sealed class SearchAction : Action {

    class SearchClickAction(val query: String) : SearchAction()

    class SearchQueryChangeAction(val query: String) : SearchAction()

    object LoadSuggestionsAction : SearchAction()

    object SearchLoadingAction : SearchAction()

    class SearchSuccessAction(val data: String) : SearchAction()

    class SuggestionsLoadedAction(val suggestions: List<String>) : SearchAction()
}
