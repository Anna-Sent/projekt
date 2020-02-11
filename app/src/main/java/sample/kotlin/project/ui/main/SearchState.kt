package sample.kotlin.project.ui.main

data class SearchState(
    val loading: Boolean = false,
    val data: String? = null,
    val throwable: Throwable? = null,
    val suggestions: List<String>? = null
) : State
