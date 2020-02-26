package sample.kotlin.project.domain.repositories.search

import io.reactivex.Single

interface SearchRepository {

    fun search(request: SearchRequest): Single<SearchResponse>
}
