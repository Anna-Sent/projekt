package sample.kotlin.project.domain.stores.splash

import dagger.Binds
import dagger.Module
import sample.kotlin.project.data.sources.core.schedulers.SchedulersDataModule
import sample.kotlin.project.domain.core.mvi.Store
import sample.kotlin.project.domain.stores.splash.entities.SplashAction
import sample.kotlin.project.domain.stores.splash.entities.SplashEvent
import sample.kotlin.project.domain.stores.splash.entities.SplashNavigationCommand
import sample.kotlin.project.domain.stores.splash.entities.SplashState

@Module(
    includes = [
        SchedulersDataModule::class
    ]
)
interface SplashModule {

    @Binds
    fun bindStore(store: SplashStore):
            Store<SplashState, SplashAction, SplashEvent, SplashNavigationCommand>
}
