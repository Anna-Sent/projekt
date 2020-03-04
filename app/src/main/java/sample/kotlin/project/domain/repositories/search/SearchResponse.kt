package sample.kotlin.project.domain.repositories.search

import sample.kotlin.project.domain.pojo.search.Repositories

data class SearchResponse(
    val request: SearchRequest,
    val repositories: Repositories,
    val nextPage: Int,
    val lastPage: Int
)
