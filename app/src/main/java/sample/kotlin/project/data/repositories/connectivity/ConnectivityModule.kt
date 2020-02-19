package sample.kotlin.project.data.repositories.connectivity

import dagger.Binds
import dagger.Module
import sample.kotlin.project.domain.repositories.connectivity.ConnectivityRepository
import javax.inject.Singleton

@Module
interface ConnectivityModule {

    @Binds
    @Singleton
    fun bindConnectivityRepository(repository: ConnectivityDataRepository): ConnectivityRepository
}
