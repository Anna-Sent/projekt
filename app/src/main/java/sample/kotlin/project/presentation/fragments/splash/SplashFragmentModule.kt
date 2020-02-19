package sample.kotlin.project.presentation.fragments.splash

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import sample.kotlin.project.domain.core.Mapper
import sample.kotlin.project.domain.core.scopes.FragmentScope
import sample.kotlin.project.domain.stores.splash.entities.SplashState
import sample.kotlin.project.presentation.core.StateSaver
import sample.kotlin.project.presentation.core.di.ViewModelKey
import sample.kotlin.project.presentation.core.di.ViewModelModule
import sample.kotlin.project.presentation.fragments.splash.state.SplashStateFromParcelableMapper
import sample.kotlin.project.presentation.fragments.splash.state.SplashStateParcelable
import sample.kotlin.project.presentation.fragments.splash.state.SplashStateToParcelableMapper

@Module
interface SplashFragmentModule {

    @FragmentScope
    @ContributesAndroidInjector(
        modules = [
            Binding::class,
            Providing::class,
            ViewModelModule::class
        ]
    )
    fun bindSearchFragment(): SplashFragment

    @Module
    interface Binding {

        @Binds
        @IntoMap
        @ViewModelKey(SplashViewModel::class)
        fun bindViewModel(viewModel: SplashViewModel): ViewModel

        @Binds
        fun bindToParcelableMapper(mapper: SplashStateToParcelableMapper):
                Mapper<SplashState, SplashStateParcelable>

        @Binds
        fun bindFromParcelableMapper(mapper: SplashStateFromParcelableMapper):
                Mapper<SplashStateParcelable, SplashState>
    }

    @Module
    class Providing {

        @FragmentScope
        @Provides
        fun provideStateSaver(
            toParcelableMapper: Mapper<SplashState, SplashStateParcelable>,
            fromParcelableMapper: Mapper<SplashStateParcelable, SplashState>
        ) = StateSaver(toParcelableMapper, fromParcelableMapper)

        @Provides
        fun provideInitialState(saver: StateSaver<SplashState, SplashStateParcelable>) =
            saver.stateOrDefault { SplashState }
    }
}