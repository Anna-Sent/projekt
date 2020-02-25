package sample.kotlin.project.data.network.http.services

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import sample.kotlin.project.data.network.http.dto.search.RepositoriesDto

interface GitHubSearchHttpService {

    @GET("search/repositories")
    fun searchRepositories(
        @Query("q") query: String,
        @Query("sort") sort: String,
        @Query("order") order: String
    ): Single<Response<RepositoriesDto>>

    @GET("search/repositories")
    fun searchRepositories(
        @Query("q") query: String,
        @Query("sort") sort: String,
        @Query("order") order: String,
        @Query("page") page: Int
    ): Single<Response<RepositoriesDto>>
}
