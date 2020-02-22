package sample.kotlin.project.domain.stores.main

import dagger.Binds
import dagger.Module
import sample.kotlin.project.domain.core.mvi.Store
import sample.kotlin.project.domain.stores.main.pojo.MainAction
import sample.kotlin.project.domain.stores.main.pojo.MainEvent
import sample.kotlin.project.domain.stores.main.pojo.MainNavigationCommand
import sample.kotlin.project.domain.stores.main.pojo.MainState

@Module
interface MainModule {

    @Binds
    fun bindStore(store: MainStore):
            Store<MainState, MainAction, MainEvent, MainNavigationCommand>
}
