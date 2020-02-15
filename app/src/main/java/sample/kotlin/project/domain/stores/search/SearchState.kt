package sample.kotlin.project.domain.stores.search

import sample.kotlin.project.domain.core.mvi.State

data class SearchState(
    val loading: Boolean = false,
    val data: String? = null,
    val suggestions: List<String> = emptyList()
) : State