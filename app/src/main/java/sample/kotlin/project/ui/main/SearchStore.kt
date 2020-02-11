package sample.kotlin.project.ui.main

class SearchStore : Store<Action, SearchState>(
    SearchReducer(),
    listOf(SearchMiddleware(), SuggestionsMiddleware()),
    SearchState()
)
