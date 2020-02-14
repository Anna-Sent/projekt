package sample.kotlin.project.data.network

import dagger.Binds
import dagger.Module
import sample.kotlin.project.data.network.connectivity.NetworkConnectivityHelper
import sample.kotlin.project.data.network.connectivity.NetworkConnectivityHelperImpl
import javax.inject.Singleton

@Module
interface NetworkModule {

    @Binds
    @Singleton
    fun bindNetworkConnectivityHelperSource(helper: NetworkConnectivityHelperImpl):
            NetworkConnectivityHelper
}
