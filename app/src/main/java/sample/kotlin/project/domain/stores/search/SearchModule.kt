package sample.kotlin.project.domain.stores.search

import dagger.Binds
import dagger.Module
import sample.kotlin.project.data.sources.request.RequestModule
import sample.kotlin.project.data.sources.search.SearchModule
import sample.kotlin.project.domain.core.mvi.Store
import sample.kotlin.project.domain.stores.search.entities.SearchAction
import sample.kotlin.project.domain.stores.search.entities.SearchEvent
import sample.kotlin.project.domain.stores.search.entities.SearchNavigationCommand
import sample.kotlin.project.domain.stores.search.entities.SearchState

@Module(
    includes = [
        SearchModule::class,
        RequestModule::class
    ]
)
interface SearchModule {

    @Binds
    fun bindStore(store: SearchStore):
            Store<SearchState, SearchAction, SearchEvent, SearchNavigationCommand>
}
