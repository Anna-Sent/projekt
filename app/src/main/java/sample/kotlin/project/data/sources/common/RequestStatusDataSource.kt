package sample.kotlin.project.data.sources.common

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import sample.kotlin.project.domain.sources.common.RequestStatusSource
import sample.kotlin.project.domain.sources.common.RequestType
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject

class RequestStatusDataSource
@Inject constructor(
) : RequestStatusSource {

    private val map = ConcurrentHashMap<RequestType, Boolean>()
    private val changed = PublishRelay.create<Boolean>()

    override fun started(requestType: RequestType) {
        map[requestType] = true
        changed.accept(true)
    }

    override fun finished(requestType: RequestType) {
        map.remove(requestType)
        changed.accept(true)
    }

    override fun status(requestType: RequestType): Observable<Boolean> =
        changed.map { map.get(requestType, false) }.distinctUntilChanged()
}

private fun <K, V> ConcurrentHashMap<K, V>.get(key: K, defaultValue: V): V =
    get(key) ?: defaultValue
