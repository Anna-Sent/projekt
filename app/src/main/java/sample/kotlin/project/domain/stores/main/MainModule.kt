package sample.kotlin.project.domain.stores.main

import dagger.Binds
import dagger.Module
import sample.kotlin.project.data.sources.core.connectivity.ConnectivityDataModule
import sample.kotlin.project.data.sources.core.schedulers.SchedulersDataModule
import sample.kotlin.project.domain.core.mvi.Store
import sample.kotlin.project.domain.stores.main.entities.MainAction
import sample.kotlin.project.domain.stores.main.entities.MainEvent
import sample.kotlin.project.domain.stores.main.entities.MainNavigationCommand
import sample.kotlin.project.domain.stores.main.entities.MainState

@Module(
    includes = [
        SchedulersDataModule::class,
        ConnectivityDataModule::class
    ]
)
interface MainModule {

    @Binds
    fun bindStore(store: MainStore):
            Store<MainState, MainAction, MainEvent, MainNavigationCommand>
}
