package sample.kotlin.project.domain.repositories.search

import io.reactivex.Observable
import io.reactivex.Single
import sample.kotlin.project.domain.pojo.search.Repositories

interface SearchRepository {

    fun search(query: String): Single<Repositories>

    fun isSearchRunning(): Observable<Boolean>
}
