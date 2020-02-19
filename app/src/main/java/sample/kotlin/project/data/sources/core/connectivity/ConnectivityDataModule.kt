package sample.kotlin.project.data.sources.core.connectivity

import dagger.Binds
import dagger.Module
import sample.kotlin.project.domain.sources.core.connectivity.ConnectivitySource
import javax.inject.Singleton

@Module
interface ConnectivityDataModule {

    @Binds
    @Singleton
    fun bindConnectivitySource(source: ConnectivityDataSource): ConnectivitySource
}
