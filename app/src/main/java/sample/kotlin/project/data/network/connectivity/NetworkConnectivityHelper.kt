package sample.kotlin.project.data.network.connectivity

import io.reactivex.Observable
import io.reactivex.Single

interface NetworkConnectivityHelper {

    fun isNetworkConnected(): Observable<Boolean>

    fun isNetworkConnectedSkipInitial(): Observable<Boolean>

    fun checkNetworkConnectedOrThrow(): Single<Boolean>

    fun setNetworkConnected(networkConnected: Boolean)

    fun isNetworkConnectedBlocking(): Boolean
}
