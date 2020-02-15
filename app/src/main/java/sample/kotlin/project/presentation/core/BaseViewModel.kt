package sample.kotlin.project.presentation.core

import androidx.lifecycle.ViewModel
import sample.kotlin.project.domain.core.mvi.*

abstract class BaseViewModel<S : State, A : Action, E : Event>
constructor(private val store: Store<A, S, E>) : ViewModel() {

    private val wiring = store.wire()

    fun bind(view: MviView<A, S, E>) = store.bind(view)

    override fun onCleared() {
        wiring.dispose()
    }
}
