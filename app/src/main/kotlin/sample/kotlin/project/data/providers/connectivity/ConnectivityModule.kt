package sample.kotlin.project.data.providers.connectivity

import dagger.Binds
import dagger.Module
import sample.kotlin.project.domain.providers.connectivity.ConnectivityProvider
import javax.inject.Singleton

@Module
interface ConnectivityModule {

    @Suppress("unused")
    @Binds
    @Singleton
    fun bindConnectivityProvider(source: ConnectivityDataProvider): ConnectivityProvider
}
