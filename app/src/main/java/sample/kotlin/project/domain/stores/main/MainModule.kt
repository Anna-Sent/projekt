package sample.kotlin.project.domain.stores.main

import dagger.Binds
import dagger.Module
import sample.kotlin.project.domain.core.mvi.Store
import sample.kotlin.project.domain.stores.main.data.MainAction
import sample.kotlin.project.domain.stores.main.data.MainEvent
import sample.kotlin.project.domain.stores.main.data.MainState

@Module
interface MainModule {

    @Binds
    fun bindStore(store: MainStore): Store<MainAction, MainState, MainEvent>
}
