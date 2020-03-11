package sample.kotlin.project.domain.stores.search.pojo

import sample.kotlin.project.domain.core.mvi.pojo.State
import sample.kotlin.project.domain.pojo.search.RepositoryItem

data class SearchState(
    val connected: Boolean = false,
    val requestType: SearchRequestType? = null,
    val lastQuery: String? = null,
    val lastLoadedPage: Int = 0,
    val nextPage: Int = 0,
    val lastPage: Int = 0,
    val repositories: List<RepositoryItem> = emptyList(),
    val error: Throwable? = null,
    val suggestions: List<String> = emptyList()
) : State {

    val hasNext = nextPage < lastPage
}
