package sample.kotlin.project.presentation.core

import androidx.lifecycle.LiveDataReactiveStreams.fromPublisher
import androidx.lifecycle.ViewModel
import io.reactivex.BackpressureStrategy
import sample.kotlin.project.domain.core.mvi.Action
import sample.kotlin.project.domain.core.mvi.Event
import sample.kotlin.project.domain.core.mvi.State
import sample.kotlin.project.domain.core.mvi.Store

abstract class BaseViewModel<S : State, A : Action, E : Event>
constructor(private val store: Store<A, S, E>) : ViewModel() {

    val statesLiveData
        get() = fromPublisher(store.statesObservable.toFlowable(BackpressureStrategy.LATEST))
    val eventsLiveData
        get() = fromPublisher(store.eventsObservable.toFlowable(BackpressureStrategy.BUFFER))

    override fun onCleared() {
        store.dispose()
    }

    internal fun postAction(action: A) {
        store.postAction(action)
    }
}
