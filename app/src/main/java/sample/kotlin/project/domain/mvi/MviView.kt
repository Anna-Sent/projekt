package sample.kotlin.project.domain.mvi

import io.reactivex.Observable

interface MviView<A : Action, S : State> {

    val actions: Observable<A>

    fun render(state: S)
}
