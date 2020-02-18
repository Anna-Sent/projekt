package sample.kotlin.project.presentation.activities.main

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import sample.kotlin.project.R
import sample.kotlin.project.domain.core.Mapper
import sample.kotlin.project.domain.core.scopes.ActivityScope
import sample.kotlin.project.domain.stores.main.data.MainState
import sample.kotlin.project.presentation.activities.main.state.MainStateFromParcelableMapper
import sample.kotlin.project.presentation.activities.main.state.MainStateParcelable
import sample.kotlin.project.presentation.activities.main.state.MainStateToParcelableMapper
import sample.kotlin.project.presentation.core.StateSaver
import sample.kotlin.project.presentation.core.ViewModelKey
import sample.kotlin.project.presentation.core.ViewModelModule
import sample.kotlin.project.presentation.fragments.FragmentsModule

@Module
interface MainActivityModule {

    @ActivityScope
    @ContributesAndroidInjector(
        modules = [
            Binding::class,
            Providing::class,
            ViewModelModule::class,
            FragmentsModule::class
        ]
    )
    fun bindMainActivity(): MainActivity

    @Module
    interface Binding {

        @Binds
        @IntoMap
        @ViewModelKey(MainViewModel::class)
        fun bindViewModel(viewModel: MainViewModel): ViewModel

        @Binds
        fun bindToParcelableMapper(mapper: MainStateToParcelableMapper):
                Mapper<MainState, MainStateParcelable>

        @Binds
        fun bindFromParcelableMapper(mapper: MainStateFromParcelableMapper):
                Mapper<MainStateParcelable, MainState>
    }

    @Module
    class Providing {

        @Provides
        internal fun provideNavigator(activity: MainActivity) =
            MainNavigator(activity, R.id.container)

        @ActivityScope
        @Provides
        fun provideStateSaver(
            toParcelableMapper: Mapper<MainState, MainStateParcelable>,
            fromParcelableMapper: Mapper<MainStateParcelable, MainState>
        ) = StateSaver(toParcelableMapper, fromParcelableMapper)

        @Provides
        fun provideInitialState(saver: StateSaver<MainState, MainStateParcelable>) =
            saver.stateOrDefault { MainState }
    }
}
