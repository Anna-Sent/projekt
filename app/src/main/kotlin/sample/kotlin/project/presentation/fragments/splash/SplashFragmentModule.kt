package sample.kotlin.project.presentation.fragments.splash

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import sample.kotlin.project.domain.core.mappers.Mapper
import sample.kotlin.project.domain.core.scopes.FragmentScope
import sample.kotlin.project.domain.stores.splash.pojo.SplashState
import sample.kotlin.project.presentation.core.di.ViewModelKey
import sample.kotlin.project.presentation.core.di.ViewModelModule
import sample.kotlin.project.presentation.core.views.StateSaver

@Suppress("unused")
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
    fun bindSplashFragment(): SplashFragment

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
        internal fun provideStateSaver(
            toParcelableMapper: Mapper<SplashState, SplashStateParcelable>,
            fromParcelableMapper: Mapper<SplashStateParcelable, SplashState>
        ) = StateSaver(
            toParcelableMapper,
            fromParcelableMapper
        )

        @Provides
        internal fun provideInitialState(saver: StateSaver<SplashState, SplashStateParcelable>) =
            saver.stateOrDefault { SplashState }
    }
}
