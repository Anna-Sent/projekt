package sample.kotlin.project.data.sources.search

import io.reactivex.Single
import sample.kotlin.project.data.network.http.dto.search.RepositoriesDto
import sample.kotlin.project.data.network.http.services.GitHubSearchHttpService
import sample.kotlin.project.domain.core.mappers.Mapper
import sample.kotlin.project.domain.pojo.search.Repositories
import sample.kotlin.project.domain.sources.search.SearchSource
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchDataSource
@Inject constructor(
    private val gitHubSearchHttpService: GitHubSearchHttpService,
    private val repositoriesMapper: Mapper<RepositoriesDto, Repositories>
) : SearchSource {

    override fun search(query: String) =
        gitHubSearchHttpService.searchRepositories(query, "starts", "desc")
            .map { response ->
                repositoriesMapper.map(response.body()!!)
            }

    override fun suggestions() =
        Single.timer(1, TimeUnit.SECONDS)
            .map { listOf("Sugg1", "Sugg2") }
}
