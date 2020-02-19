package sample.kotlin.project.presentation.core

import androidx.lifecycle.ViewModel
import io.logging.LogSystem
import org.slf4j.LoggerFactory
import sample.kotlin.project.domain.core.mvi.Action
import sample.kotlin.project.domain.core.mvi.Event
import sample.kotlin.project.domain.core.mvi.State
import sample.kotlin.project.domain.core.mvi.Store

abstract class BaseViewModel<S : State, A : Action, E : Event>
constructor(
    private val store: Store<A, S, E>
) : ViewModel() {

    final override fun toString() = super.toString()
    protected val logger = LoggerFactory.getLogger(toString())
    protected fun unexpectedError(throwable: Throwable) {
        logger.error("Unexpected error occurred", throwable)
        LogSystem.report(logger, "Unexpected error occurred", throwable)
    }

    internal val statesObservable = store.statesObservable
    internal val eventsHolder = store.eventsHolder

    override fun onCleared() {
        logger.debug("cleared view model {}", this)
        store.dispose()
    }

    internal fun postAction(action: A) {
        store.postAction(action)
    }
}
