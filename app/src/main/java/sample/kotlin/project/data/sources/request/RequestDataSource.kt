package sample.kotlin.project.data.sources.request

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import sample.kotlin.project.domain.sources.request.RequestSource
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject

class RequestDataSource
@Inject constructor(
) : RequestSource {

    private val map = ConcurrentHashMap<Any, Boolean>()
    private val changed = PublishRelay.create<Boolean>()

    override fun started(requestType: Any) {
        map[requestType] = true
        changed.accept(true)
    }

    override fun finished(requestType: Any) {
        map.remove(requestType)
        changed.accept(true)
    }

    override fun status(requestType: Any): Observable<Boolean> =
        changed.map { map.get(requestType, false) }.distinctUntilChanged()

    companion object {
        private fun <K, V> ConcurrentHashMap<K, V>.get(key: K, defaultValue: V) =
            get(key) ?: defaultValue
    }
}
