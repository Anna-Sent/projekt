package sample.kotlin.project.domain.stores.search

import dagger.Binds
import dagger.Module
import sample.kotlin.project.data.sources.request.RequestDataModule
import sample.kotlin.project.data.sources.search.SearchDataModule
import sample.kotlin.project.domain.core.mvi.Store
import sample.kotlin.project.domain.stores.search.entities.SearchAction
import sample.kotlin.project.domain.stores.search.entities.SearchEvent
import sample.kotlin.project.domain.stores.search.entities.SearchNavigationCommand
import sample.kotlin.project.domain.stores.search.entities.SearchState

@Module(
    includes = [
        SearchDataModule::class,
        RequestDataModule::class
    ]
)
interface SearchModule {

    @Binds
    fun bindStore(store: SearchStore):
            Store<SearchState, SearchAction, SearchEvent, SearchNavigationCommand>
}
