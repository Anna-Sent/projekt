package sample.kotlin.project.ui.main

class SearchReducer : Reducer<SearchState, Action> {

    override fun reduce(state: SearchState, action: Action) =
        when (action) {

            is InternalAction.SearchLoadingAction ->
                state.copy(
                    loading = true,
                    suggestions = null,
                    throwable = null
                )

            is InternalAction.SearchSuccessAction ->
                state.copy(
                    loading = false,
                    data = action.data,
                    throwable = null,
                    suggestions = null
                )

            is InternalAction.SearchFailureAction ->
                state.copy(
                    loading = false,
                    throwable = action.throwable
                )

            else -> state
        }
}
