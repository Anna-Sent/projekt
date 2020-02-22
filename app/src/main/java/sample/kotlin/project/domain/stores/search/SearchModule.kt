package sample.kotlin.project.domain.stores.search

import dagger.Binds
import dagger.Module
import sample.kotlin.project.domain.core.mvi.Store
import sample.kotlin.project.domain.stores.search.pojo.SearchAction
import sample.kotlin.project.domain.stores.search.pojo.SearchEvent
import sample.kotlin.project.domain.stores.search.pojo.SearchNavigationCommand
import sample.kotlin.project.domain.stores.search.pojo.SearchState

@Module
interface SearchModule {

    @Binds
    fun bindStore(store: SearchStore):
            Store<SearchState, SearchAction, SearchEvent, SearchNavigationCommand>
}
