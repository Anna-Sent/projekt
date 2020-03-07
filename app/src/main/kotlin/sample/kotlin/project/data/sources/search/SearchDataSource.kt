package sample.kotlin.project.data.sources.search

import io.reactivex.Single
import retrofit2.Response
import sample.kotlin.project.data.network.http.dto.search.RepositoriesDto
import sample.kotlin.project.data.network.http.services.GitHubSearchHttpService
import sample.kotlin.project.domain.core.mappers.Mapper
import sample.kotlin.project.domain.pojo.search.Repositories
import sample.kotlin.project.domain.providers.schedulers.SchedulersProvider
import sample.kotlin.project.domain.repositories.search.SearchRequest
import sample.kotlin.project.domain.repositories.search.SearchResponse
import sample.kotlin.project.domain.sources.search.SearchSource
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchDataSource
@Inject constructor(
    private val schedulersProvider: SchedulersProvider,
    private val gitHubSearchHttpService: GitHubSearchHttpService,
    private val repositoriesMapper: Mapper<RepositoriesDto, Repositories>
) : SearchSource {

    companion object {
        private const val SORT = "stars"
        private const val ORDER = "desc"
    }

    override fun search(request: SearchRequest) =
        searchRepositories(request)
            .map {
                val pageLinks = PageLinks(it)
                val nextPage = pageLinks.next ?: request.page
                val lastPage = pageLinks.last ?: request.page
                SearchResponse(
                    request, repositoriesMapper.map(it.body()!!),
                    nextPage,
                    lastPage
                )
            }

            .map { response ->
                response.copy(
                    repositories =
                    Repositories(
                        response.repositories.totalCount,
                        response.repositories.items.withIndex().map { indexedValue ->
                            indexedValue.value.copy(
                                pageIndexToDebug = indexedValue.index,
                                pageNumberToDebug = request.page
                            )
                        })
                )
            }

    private fun searchRepositories(request: SearchRequest): Single<Response<RepositoriesDto>> {
        return if (request.page == 1) {
            gitHubSearchHttpService.searchRepositories(request.query, SORT, ORDER)
        } else {
            gitHubSearchHttpService.searchRepositories(request.query, SORT, ORDER, request.page)
        }
    }

    override fun suggestions() =
        Single.timer(1, TimeUnit.SECONDS, schedulersProvider.ioScheduler)
            .map { listOf("Sugg1", "Sugg2") }
}
