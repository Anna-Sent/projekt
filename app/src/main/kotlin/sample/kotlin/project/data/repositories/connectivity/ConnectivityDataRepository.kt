package sample.kotlin.project.data.repositories.connectivity

import sample.kotlin.project.domain.providers.connectivity.ConnectivityProvider
import sample.kotlin.project.domain.repositories.connectivity.ConnectivityRepository
import javax.inject.Inject

class ConnectivityDataRepository
@Inject constructor(
    private val connectivityProvider: ConnectivityProvider
) : ConnectivityRepository {

    override fun networkConnected() = connectivityProvider.networkConnected()

    override fun setNetworkConnected(networkConnected: Boolean) =
        connectivityProvider.setNetworkConnected(networkConnected)
}
