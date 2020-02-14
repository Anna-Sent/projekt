package sample.kotlin.project.domain.core.mvi

import io.reactivex.Observable
import io.reactivex.functions.Consumer

interface MviView<A : Action, S : State, E : Event> {

    val actions: Observable<A>

    val events: Consumer<E>

    fun render(state: S)
}
