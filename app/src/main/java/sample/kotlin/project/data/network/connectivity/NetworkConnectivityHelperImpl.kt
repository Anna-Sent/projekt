package sample.kotlin.project.data.network.connectivity

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.Relay
import io.logging.utils.DeviceUtils
import io.reactivex.Observable
import io.reactivex.Single
import sample.kotlin.project.domain.exceptions.NoConnectionException
import javax.inject.Inject

class NetworkConnectivityHelperImpl
@Inject constructor(
    private val connectivityManager: ConnectivityManager
) : NetworkConnectivityHelper {

    private val connectedSource: Relay<Boolean>

    init {
        connectedSource = BehaviorRelay.createDefault(isNetworkConnectedBlocking())
    }

    override fun isNetworkConnected(): Observable<Boolean> = isNetworkConnectedObservable

    override fun isNetworkConnectedSkipInitial(): Observable<Boolean> =
        isNetworkConnectedObservable
            .skip(1)

    override fun checkNetworkConnectedOrThrow() =
        isNetworkConnectedSingle
            .flatMap { if (it) Single.just(true) else Single.error(NoConnectionException()) }

    override fun setNetworkConnected(networkConnected: Boolean) =
        connectedSource.accept(networkConnected)

    override fun isNetworkConnectedBlocking() =
        if (DeviceUtils.isAtLeastMarshmallow()) {
            val network = connectivityManager.activeNetwork
            val capabilities =
                connectivityManager.getNetworkCapabilities(network)
            capabilities != null
                    && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                    || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            networkInfo != null && networkInfo.isConnected
        }

    private val isNetworkConnectedSingle
        get() = isNetworkConnectedObservable
            .firstOrError()

    private val isNetworkConnectedObservable
        get() = connectedSource
            .distinctUntilChanged()
            .hide()
}
