package sample.kotlin.project.domain.providers.connectivity

import io.reactivex.Observable
import io.reactivex.Single

interface ConnectivityProvider {

    fun networkConnected(): Observable<Boolean>

    fun networkConnectedSkipInitial(): Observable<Boolean>

    fun checkNetworkConnectedOrThrow(): Single<Boolean>

    fun setNetworkConnected(networkConnected: Boolean)
}
