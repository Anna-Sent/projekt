package sample.kotlin.project.data.sources.search

import io.reactivex.Single
import sample.kotlin.project.data.network.http.services.GitHubSearchHttpService
import sample.kotlin.project.domain.sources.search.SearchSource
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchDataSource
@Inject constructor(
    private val gitHubSearchHttpService: GitHubSearchHttpService
) : SearchSource {

    override fun search(query: String) =
        gitHubSearchHttpService.searchRepositories(query, "starts", "desc")
            .map { it.string() }

    override fun suggestions() =
        Single.timer(1, TimeUnit.SECONDS)
            .map { listOf("Sugg1", "Sugg2") }
}
