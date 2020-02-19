package sample.kotlin.project.domain.sources.core.connectivity

import io.reactivex.Observable
import io.reactivex.Single

interface ConnectivityProvider {

    fun isNetworkConnected(): Observable<Boolean>

    fun isNetworkConnectedSkipInitial(): Observable<Boolean>

    fun checkNetworkConnectedOrThrow(): Single<Boolean>

    fun setNetworkConnected(networkConnected: Boolean)
}
