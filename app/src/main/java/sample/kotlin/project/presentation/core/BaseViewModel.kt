package sample.kotlin.project.presentation.core

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.Disposable
import sample.kotlin.project.domain.mvi.Action
import sample.kotlin.project.domain.mvi.MviView
import sample.kotlin.project.domain.mvi.State
import sample.kotlin.project.domain.mvi.Store

abstract class BaseViewModel<S : State, A : Action>
constructor(private val store: Store<A, S>) : ViewModel() {

    private val wiring = store.wire()
    private var viewBinding: Disposable? = null

    override fun onCleared() {
        wiring.dispose()
    }

    fun bind(view: MviView<A, S>) {
        viewBinding = store.bind(view)
    }

    fun unbind() {
        viewBinding?.dispose()
    }
}
