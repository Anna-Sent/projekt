package sample.kotlin.project.domain.stores.search.pojo

import sample.kotlin.project.domain.core.mvi.pojo.State
import sample.kotlin.project.domain.pojo.search.Repository

data class SearchState(
    val connected: Boolean = false,
    val loading: Boolean = false,
    val lastQuery: String = "",
    val lastLoadedPage: Int = 0,
    val repositories: List<Repository> = emptyList(),
    val suggestions: List<String> = emptyList()
) : State
