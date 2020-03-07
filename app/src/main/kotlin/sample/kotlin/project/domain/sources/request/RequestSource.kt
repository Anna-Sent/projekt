package sample.kotlin.project.domain.sources.request

import io.reactivex.Observable
import io.reactivex.SingleTransformer

interface RequestSource {

    fun started(requestType: Any)

    fun finished(requestType: Any)

    fun status(requestType: Any): Observable<Boolean>

    fun <U> applyStatusUpdating(requestType: Any): SingleTransformer<U, U> =
        SingleTransformer {
            it
                .doOnSubscribe { started(requestType) }
                .doOnEvent { _, _ -> finished(requestType) }
        }
}
