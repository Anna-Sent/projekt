package sample.kotlin.project.domain.stores.search.pojo

import sample.kotlin.project.domain.core.mvi.pojo.State

data class SearchState(
    val connected: Boolean = false,
    val loading: Boolean = false,
    val data: String? = null,
    val suggestions: List<String> = emptyList()
) : State
