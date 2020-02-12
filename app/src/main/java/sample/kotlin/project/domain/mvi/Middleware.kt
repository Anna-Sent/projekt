package sample.kotlin.project.domain.mvi

import io.reactivex.Observable

interface Middleware<A : Action, S : State> {

    fun bind(actions: Observable<A>, states: Observable<S>): Observable<A>
}
