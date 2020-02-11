package sample.kotlin.project.ui.main

import io.reactivex.Observable

interface MviView<A : Action, S : State> {

    val userActions: Observable<A>

    fun render(state: S)
}
