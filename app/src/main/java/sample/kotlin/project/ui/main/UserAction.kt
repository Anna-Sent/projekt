package sample.kotlin.project.ui.main

sealed class UserAction : Action {

    class SearchAction(val query: String) : UserAction()

    class LoadSuggestionsAction(val query: String) : UserAction()
}
