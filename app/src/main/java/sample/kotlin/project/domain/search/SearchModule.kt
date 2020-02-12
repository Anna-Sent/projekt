package sample.kotlin.project.domain.search

import dagger.Binds
import dagger.Module
import sample.kotlin.project.domain.FragmentScope
import sample.kotlin.project.domain.mvi.Store

@Module
interface SearchModule {

    @Binds
    @FragmentScope
    fun bindStore(store: SearchStore): Store<SearchAction, SearchState, SearchEvent>
}
