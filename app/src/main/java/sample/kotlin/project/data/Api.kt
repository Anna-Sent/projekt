package sample.kotlin.project.data

import io.reactivex.Single
import java.util.concurrent.TimeUnit

class Api {

    fun search(query: String) =
        Single.timer(10, TimeUnit.SECONDS)
            .map { "Result $query" }

    fun suggestions() =
        Single.timer(1, TimeUnit.SECONDS)
            .map { listOf("Sugg1", "Sugg2") }
}
