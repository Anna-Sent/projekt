package sample.kotlin.project.data.network.http

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import sample.kotlin.project.data.network.http.services.GitHubSearchHttpService
import javax.inject.Singleton

@Module(
    includes = [
        HttpModule::class
    ]
)
class HttpServices {

    @Provides
    @Singleton
    fun provideGitHubSearchHttpService(retrofit: Retrofit): GitHubSearchHttpService =
        retrofit.create(GitHubSearchHttpService::class.java)
}
