package sample.kotlin.project.data.repositories.search

import io.reactivex.Single
import sample.kotlin.project.domain.providers.connectivity.ConnectivityProvider
import sample.kotlin.project.domain.providers.schedulers.SchedulersProvider
import sample.kotlin.project.domain.repositories.search.SearchRepository
import sample.kotlin.project.domain.repositories.search.SearchRequest
import sample.kotlin.project.domain.repositories.search.SearchResponse
import sample.kotlin.project.domain.sources.search.SearchSource
import javax.inject.Inject

class SearchDataRepository
@Inject constructor(
    private val schedulersProvider: SchedulersProvider,
    private val connectivityProvider: ConnectivityProvider,
    private val searchSource: SearchSource
) : SearchRepository {

    override fun search(request: SearchRequest): Single<SearchResponse> =
        connectivityProvider.checkNetworkConnectedOrThrow()
            .flatMap { searchSource.search(request) }
            .subscribeOn(schedulersProvider.ioScheduler)
}
