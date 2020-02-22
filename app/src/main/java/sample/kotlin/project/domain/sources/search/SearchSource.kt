package sample.kotlin.project.domain.sources.search

import io.reactivex.Single
import sample.kotlin.project.domain.pojo.search.Repositories

interface SearchSource {

    fun search(query: String): Single<Repositories>

    fun suggestions(): Single<List<String>>
}
