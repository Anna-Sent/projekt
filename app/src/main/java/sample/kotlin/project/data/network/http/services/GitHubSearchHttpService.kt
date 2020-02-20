package sample.kotlin.project.data.network.http.services

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubSearchHttpService {

    @GET("search/repositories")
    fun searchRepositories(
        @Query("q") query: String,
        @Query("sort") sort: String,
        @Query("order") order: String
    ): Single<ResponseBody>
}
