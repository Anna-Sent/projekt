package sample.kotlin.project.domain.repositories.connectivity

import io.reactivex.Observable

interface ConnectivityRepository {

    fun networkConnected(): Observable<Boolean>

    fun setNetworkConnected(networkConnected: Boolean)
}
