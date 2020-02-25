package sample.kotlin.project.domain.sources.search

import io.reactivex.Single
import sample.kotlin.project.domain.repositories.search.SearchRequest
import sample.kotlin.project.domain.repositories.search.SearchResponse

interface SearchSource {

    fun search(request: SearchRequest): Single<SearchResponse>

    fun suggestions(): Single<List<String>>
}
