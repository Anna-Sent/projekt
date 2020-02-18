package sample.kotlin.project.data.sources.common

import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.Relay
import io.reactivex.Observable
import sample.kotlin.project.domain.sources.common.RequestStatusLocalSource
import sample.kotlin.project.domain.sources.common.RequestType
import javax.inject.Inject

class RequestStatusLocalDataSource
@Inject constructor(
) : RequestStatusLocalSource {

    private val map = mapOf<RequestType, Relay<Boolean>>(
        RequestType.Search to BehaviorRelay.createDefault(false)
    )

    override fun started(requestType: RequestType) {
        map.getValue(requestType).accept(true)
    }

    override fun finished(requestType: RequestType) {
        map.getValue(requestType).accept(false)
    }

    override fun status(requestType: RequestType): Observable<Boolean> =
        map.getValue(requestType).distinctUntilChanged()
}
