package sample.kotlin.project.presentation.fragments.search

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import sample.kotlin.project.domain.core.Mapper
import sample.kotlin.project.domain.core.scopes.FragmentScope
import sample.kotlin.project.domain.stores.search.entities.SearchState
import sample.kotlin.project.presentation.core.StateSaver
import sample.kotlin.project.presentation.core.ViewModelKey
import sample.kotlin.project.presentation.core.ViewModelModule
import sample.kotlin.project.presentation.fragments.search.state.SearchStateFromParcelableMapper
import sample.kotlin.project.presentation.fragments.search.state.SearchStateParcelable
import sample.kotlin.project.presentation.fragments.search.state.SearchStateToParcelableMapper

@Module
interface SearchFragmentModule {

    @FragmentScope
    @ContributesAndroidInjector(
        modules = [
            Binding::class,
            Providing::class,
            ViewModelModule::class
        ]
    )
    fun bindSearchFragment(): SearchFragment

    @Module
    interface Binding {

        @Binds
        @IntoMap
        @ViewModelKey(SearchViewModel::class)
        fun bindViewModel(viewModel: SearchViewModel): ViewModel

        @Binds
        fun bindToParcelableMapper(mapper: SearchStateToParcelableMapper):
                Mapper<SearchState, SearchStateParcelable>

        @Binds
        fun bindFromParcelableMapper(mapper: SearchStateFromParcelableMapper):
                Mapper<SearchStateParcelable, SearchState>
    }

    @Module
    class Providing {

        @FragmentScope
        @Provides
        fun provideStateSaver(
            toParcelableMapper: Mapper<SearchState, SearchStateParcelable>,
            fromParcelableMapper: Mapper<SearchStateParcelable, SearchState>
        ) = StateSaver(toParcelableMapper, fromParcelableMapper)

        @Provides
        fun provideInitialState(saver: StateSaver<SearchState, SearchStateParcelable>) =
            saver.stateOrDefault { SearchState() }
    }
}
