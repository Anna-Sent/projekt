package sample.kotlin.project.data.sources.search

import dagger.Binds
import dagger.Module
import sample.kotlin.project.domain.sources.search.SearchSource
import javax.inject.Singleton

@Module(
    includes = [
        SearchMappersModule::class
    ]
)
interface SearchModule {

    @Binds
    @Singleton
    fun bindSearchSource(source: SearchDataSource): SearchSource
}
