package sample.kotlin.project.presentation.fragments.search

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import sample.kotlin.project.domain.FragmentScope
import sample.kotlin.project.domain.Mapper
import sample.kotlin.project.domain.search.SearchModule
import sample.kotlin.project.domain.search.SearchState
import sample.kotlin.project.presentation.core.StateSaver
import sample.kotlin.project.presentation.core.ViewModelKey
import sample.kotlin.project.presentation.core.ViewModelModule

@Module
interface SearchFragmentModule {

    @FragmentScope
    @ContributesAndroidInjector(
        modules = [
            Binding::class,
            Providing::class,
            ViewModelModule::class,
            SearchModule::class
        ]
    )
    fun contributeHomeFragmentInjector(): SearchFragment

    @Module
    interface Binding {

        @Binds
        @FragmentScope
        @IntoMap
        @ViewModelKey(SearchViewModel::class)
        fun bindViewModel(viewModel: SearchViewModel): ViewModel

        @Binds
        @FragmentScope
        fun bindToParcelableMapper(mapper: SearchStateToParcelableMapper): Mapper<SearchState, SearchStateParcelable>

        @Binds
        @FragmentScope
        fun bindFromParcelableMapper(mapper: SearchStateFromParcelableMapper): Mapper<SearchStateParcelable, SearchState>
    }

    @Module
    class Providing {

        @Provides
        @FragmentScope
        fun initialState(saver: StateSaver<SearchState, SearchStateParcelable>) =
            saver.getStateOrDefault { SearchState() }
    }
}
