package sample.kotlin.project.data.providers.connectivity

import dagger.Binds
import dagger.Module
import sample.kotlin.project.domain.sources.core.connectivity.ConnectivityProvider
import javax.inject.Singleton

@Module
interface ConnectivityModule {

    @Binds
    @Singleton
    fun bindConnectivitySource(source: ConnectivityDataProvider): ConnectivityProvider
}
