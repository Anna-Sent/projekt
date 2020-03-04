package sample.kotlin.project.data.sources.search

import io.reactivex.Single
import retrofit2.Response
import sample.kotlin.project.data.network.http.dto.search.RepositoriesDto
import sample.kotlin.project.data.network.http.services.GitHubSearchHttpService
import sample.kotlin.project.domain.core.mappers.Mapper
import sample.kotlin.project.domain.pojo.search.Repositories
import sample.kotlin.project.domain.repositories.search.SearchRequest
import sample.kotlin.project.domain.repositories.search.SearchResponse
import sample.kotlin.project.domain.sources.search.SearchSource
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchDataSource
@Inject constructor(
    private val gitHubSearchHttpService: GitHubSearchHttpService,
    private val repositoriesMapper: Mapper<RepositoriesDto, Repositories>
) : SearchSource {

    companion object {
        private const val SORT = "stars"
        private const val ORDER = "desc"
    }

    override fun search(request: SearchRequest) =
        searchRepositories(request)
            .map { repositoriesMapper.map(it.body()!!) }

            .map { repositories ->
                Repositories(
                    repositories.totalCount,
                    repositories.items.withIndex().map { indexedValue ->
                        indexedValue.value.copy(pageIndexToDebug = indexedValue.index)
                    })
            }

            .map { SearchResponse(request, it) }

    private fun searchRepositories(request: SearchRequest): Single<Response<RepositoriesDto>> {
        return if (request.page == 1)
            gitHubSearchHttpService.searchRepositories(request.query, SORT, ORDER)
        else
            gitHubSearchHttpService.searchRepositories(request.query, SORT, ORDER, request.page)
    }

    override fun suggestions() =
        Single.timer(1, TimeUnit.SECONDS)
            .map { listOf("Sugg1", "Sugg2") }
}
