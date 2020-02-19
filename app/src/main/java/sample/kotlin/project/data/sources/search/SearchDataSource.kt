package sample.kotlin.project.data.sources.search

import io.reactivex.Single
import sample.kotlin.project.domain.sources.search.SearchSource
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchDataSource
@Inject constructor(
) : SearchSource {

    override fun search(query: String) =
        Single.timer(10, TimeUnit.SECONDS)
            .map { "Result $query" }
            .flatMap<String> { Single.error(IllegalArgumentException()) }

    override fun suggestions() =
        Single.timer(1, TimeUnit.SECONDS)
            .map { listOf("Sugg1", "Sugg2") }
}
