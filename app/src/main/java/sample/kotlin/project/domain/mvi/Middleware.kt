package sample.kotlin.project.domain.mvi

import io.reactivex.Observable
import io.reactivex.functions.Consumer

interface Middleware<A : Action, S : State, E : Event> {

    fun bind(actions: Observable<A>, states: Observable<S>, events: Consumer<E>): Observable<A>
}
