package sample.kotlin.project.domain.repositories.search

import io.reactivex.Observable
import io.reactivex.Single

interface SearchRepository {

    fun search(query: String): Single<String>

    fun isSearchRunning(): Observable<Boolean>
}
