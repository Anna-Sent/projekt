package sample.kotlin.project.data.repositories.search

import dagger.Binds
import dagger.Module
import sample.kotlin.project.domain.repositories.search.SearchRepository
import javax.inject.Singleton

@Module
interface SearchModule {

    @Binds
    @Singleton
    fun bindSearchRepository(repository: SearchDataRepository): SearchRepository
}
