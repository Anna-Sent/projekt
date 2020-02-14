package sample.kotlin.project.domain.sources.search

import io.reactivex.Single

interface SearchRemoteSource {

    fun search(query: String): Single<String>

    fun suggestions(): Single<List<String>>
}
