package sample.kotlin.project.domain.stores.splash

import dagger.Binds
import dagger.Module
import sample.kotlin.project.domain.core.mvi.Store
import sample.kotlin.project.domain.stores.splash.pojo.SplashAction
import sample.kotlin.project.domain.stores.splash.pojo.SplashEvent
import sample.kotlin.project.domain.stores.splash.pojo.SplashNavigationCommand
import sample.kotlin.project.domain.stores.splash.pojo.SplashState

@Module
interface SplashModule {

    @Binds
    fun bindStore(store: SplashStore):
            Store<SplashState, SplashAction, SplashEvent, SplashNavigationCommand>
}
