package sample.kotlin.project.data.network.connectivity

import android.net.ConnectivityManager
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.Relay
import io.reactivex.Observable
import io.reactivex.Single
import sample.kotlin.project.domain.exceptions.NoConnectionException
import sample.kotlin.project.domain.network.NetworkConnectivityHelper
import sample.kotlin.project.domain.network.isNetworkConnected
import javax.inject.Inject

class NetworkConnectivityHelperImpl
@Inject constructor(
    connectivityManager: ConnectivityManager
) : NetworkConnectivityHelper {

    private val connectedSource: Relay<Boolean>

    init {
        connectedSource = BehaviorRelay.createDefault(connectivityManager.isNetworkConnected)
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

    private val isNetworkConnectedObservable = connectedSource.distinctUntilChanged().hide()

    private val isNetworkConnectedSingle = isNetworkConnectedObservable.firstOrError()
}
