package sample.kotlin.project.domain.repositories.search

data class SearchRequest(
    val query: String,
    val page: Int
)
