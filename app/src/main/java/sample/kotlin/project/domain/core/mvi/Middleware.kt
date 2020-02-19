package sample.kotlin.project.domain.core.mvi

import io.reactivex.Observable
import io.reactivex.functions.Consumer
import sample.kotlin.project.domain.core.mvi.entities.Action
import sample.kotlin.project.domain.core.mvi.entities.Event
import sample.kotlin.project.domain.core.mvi.entities.State

interface Middleware<A : Action, S : State, E : Event> {

    fun bind(actions: Observable<A>, states: Observable<S>, events: Consumer<E>): Observable<A>
}
