package sample.kotlin.project.domain.sources.common

import io.reactivex.Observable
import io.reactivex.SingleTransformer

interface RequestStatusLocalSource {

    fun started(requestType: RequestType)

    fun finished(requestType: RequestType)

    fun status(requestType: RequestType): Observable<Boolean>

    fun <U> applyStatusUpdating(requestType: RequestType): SingleTransformer<U, U> =
        SingleTransformer {
            it
                .doOnSubscribe { started(requestType) }
                .doOnEvent { _, _ -> finished(requestType) }
        }
}
