package sample.kotlin.project.presentation.core

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.Disposable
import sample.kotlin.project.domain.mvi.*

abstract class BaseViewModel<S : State, A : Action, E : Event>
constructor(private val store: Store<A, S, E>) : ViewModel() {

    private val wiring = store.wire()
    private var viewBinding: Disposable? = null

    override fun onCleared() {
        wiring.dispose()
    }

    fun bind(view: MviView<A, S, E>) {
        viewBinding = store.bind(view)
    }

    fun unbind() {
        viewBinding?.dispose()
    }
}
