package sample.kotlin.project.domain.repositories.connectivity

import io.reactivex.Observable

interface ConnectivityRepository {

    fun isNetworkConnected(): Observable<Boolean>

    fun setNetworkConnected(networkConnected: Boolean)
}
