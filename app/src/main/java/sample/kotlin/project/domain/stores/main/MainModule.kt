package sample.kotlin.project.domain.stores.main

import dagger.Binds
import dagger.Module
import sample.kotlin.project.data.network.connectivity.NetworkConnectivityModule
import sample.kotlin.project.domain.core.mvi.Store
import sample.kotlin.project.domain.stores.main.entities.MainAction
import sample.kotlin.project.domain.stores.main.entities.MainEvent
import sample.kotlin.project.domain.stores.main.entities.MainState

@Module(
    includes =
    [
        NetworkConnectivityModule::class
    ]
)
interface MainModule {

    @Binds
    fun bindStore(store: MainStore): Store<MainAction, MainState, MainEvent>
}
