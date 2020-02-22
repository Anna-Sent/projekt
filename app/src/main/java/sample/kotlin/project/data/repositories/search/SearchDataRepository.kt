package sample.kotlin.project.data.repositories.search

import io.reactivex.Single
import sample.kotlin.project.domain.pojo.search.Repositories
import sample.kotlin.project.domain.providers.connectivity.ConnectivityProvider
import sample.kotlin.project.domain.providers.schedulers.SchedulersProvider
import sample.kotlin.project.domain.repositories.search.SearchRepository
import sample.kotlin.project.domain.sources.request.RequestSource
import sample.kotlin.project.domain.sources.search.SearchSource
import javax.inject.Inject

class SearchDataRepository
@Inject constructor(
    private val schedulersProvider: SchedulersProvider,
    private val connectivityProvider: ConnectivityProvider,
    private val searchSource: SearchSource,
    private val requestSource: RequestSource
) : SearchRepository {

    override fun search(query: String): Single<Repositories> =
        connectivityProvider.checkNetworkConnectedOrThrow()
            .flatMap { searchSource.search(query) }
            .compose(requestSource.applyStatusUpdating(RequestType.Search))
            .subscribeOn(schedulersProvider.ioScheduler)

    override fun isSearchRunning() = requestSource.status(RequestType.Search)
}
