package sample.kotlin.project.domain.search

import sample.kotlin.project.domain.mvi.State

data class SearchState(
    val loading: Boolean = false,
    val data: String? = null,
    val throwable: Throwable? = null,
    val suggestions: List<String> = emptyList()
) : State
