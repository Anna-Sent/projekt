package sample.kotlin.project.data.sources.search

import dagger.Binds
import dagger.Module
import sample.kotlin.project.domain.sources.search.SearchRemoteSource
import javax.inject.Singleton

@Module
interface SearchDataModule {

    @Binds
    @Singleton
    fun bindSearchRemoteSource(source: SearchRemoteDataSource): SearchRemoteSource
}
