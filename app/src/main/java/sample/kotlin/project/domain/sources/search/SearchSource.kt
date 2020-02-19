package sample.kotlin.project.domain.sources.search

import io.reactivex.Single

interface SearchSource {

    fun search(query: String): Single<String>

    fun suggestions(): Single<List<String>>
}
