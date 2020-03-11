package sample.kotlin.project.data.providers.connectivity

import android.net.ConnectivityManager
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.Relay
import io.reactivex.Observable
import io.reactivex.Single
import sample.kotlin.project.domain.providers.connectivity.ConnectivityProvider
import sample.kotlin.project.domain.providers.connectivity.NoConnectionException
import sample.kotlin.project.domain.providers.connectivity.networkConnected
import javax.inject.Inject

class ConnectivityDataProvider
@Inject constructor(
    connectivityManager: ConnectivityManager
) : ConnectivityProvider {

    private val connectedRelay: Relay<Boolean>

    init {
        connectedRelay = BehaviorRelay.createDefault(connectivityManager.networkConnected)
    }

    override fun networkConnected(): Observable<Boolean> = networkConnectedObservable

    override fun networkConnectedSkipInitial(): Observable<Boolean> =
        networkConnectedObservable
            .skip(1)

    override fun checkNetworkConnectedOrThrow() =
        networkConnectedSingle
            .flatMap { if (it) Single.just(true) else Single.error(NoConnectionException()) }

    override fun setNetworkConnected(networkConnected: Boolean) =
        connectedRelay.accept(networkConnected)

    private val networkConnectedObservable = connectedRelay.distinctUntilChanged().hide()

    private val networkConnectedSingle = networkConnectedObservable.firstOrError()
}
