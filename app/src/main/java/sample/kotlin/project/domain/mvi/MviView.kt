package sample.kotlin.project.domain.mvi

import io.reactivex.Observable
import io.reactivex.functions.Consumer

interface MviView<A : Action, S : State, E : Event> {

    val actions: Observable<A>

    val eventsConsumer: Consumer<E>

    fun render(state: S)
}
