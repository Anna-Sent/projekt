package sample.kotlin.project.data.network.connectivity

import dagger.Binds
import dagger.Module
import sample.kotlin.project.domain.network.NetworkConnectivityHelper
import javax.inject.Singleton

@Module
interface NetworkConnectivityModule {

    @Binds
    @Singleton
    fun bindNetworkConnectivityHelper(helper: NetworkConnectivityHelperImpl): NetworkConnectivityHelper
}
