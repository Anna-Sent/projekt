package sample.kotlin.project.domain.stores.search

import dagger.Binds
import dagger.Module
import sample.kotlin.project.data.sources.common.CommonDataModule
import sample.kotlin.project.data.sources.search.SearchDataModule
import sample.kotlin.project.domain.core.mvi.Store
import sample.kotlin.project.domain.stores.search.data.SearchAction
import sample.kotlin.project.domain.stores.search.data.SearchEvent
import sample.kotlin.project.domain.stores.search.data.SearchState

@Module(
    includes = [
        SearchDataModule::class,
        CommonDataModule::class
    ]
)
interface SearchModule {

    @Binds
    fun bindStore(store: SearchStore): Store<SearchAction, SearchState, SearchEvent>
}
