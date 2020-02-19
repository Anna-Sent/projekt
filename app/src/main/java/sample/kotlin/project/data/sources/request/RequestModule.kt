package sample.kotlin.project.data.sources.request

import dagger.Binds
import dagger.Module
import sample.kotlin.project.domain.sources.request.RequestSource
import javax.inject.Singleton

@Module
interface RequestModule {

    @Binds
    @Singleton
    fun bindRequestSource(source: RequestDataSource): RequestSource
}
