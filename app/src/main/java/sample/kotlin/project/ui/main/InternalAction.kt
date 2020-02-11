package sample.kotlin.project.ui.main

sealed class InternalAction : Action {

    object SearchLoadingAction : InternalAction()

    class SearchSuccessAction(val data: String) : InternalAction()

    class SearchFailureAction(val throwable: Throwable) : InternalAction()

    class SuggectionsLoadedAction(val suggections: List<String>) : InternalAction()
}
