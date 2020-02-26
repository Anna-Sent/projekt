package sample.kotlin.project.domain.stores.search.pojo

import sample.kotlin.project.domain.core.mvi.pojo.State
import sample.kotlin.project.domain.pojo.search.RepositoryItem

data class SearchState(
    val connected: Boolean = false,
    val requestType: SearchRequestType? = null,
    val lastQuery: String = "",
    val lastLoadedPage: Int = 0,
    val repositories: List<RepositoryItem> = emptyList(),
    val suggestions: List<String> = emptyList()
) : State
