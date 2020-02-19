package sample.kotlin.project.presentation.core

import androidx.lifecycle.ViewModel
import io.logging.LogSystem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
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

    internal val eventsHolder = EventsHolder<S, E>()
    val statesObservable = store.statesObservable
    private val eventsObservable = store.eventsObservable
    private val disposables = CompositeDisposable()

    init {
        disposables += eventsObservable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(eventsHolder::handleEvent, ::unexpectedError)
    }

    override fun onCleared() {
        logger.debug("cleared view model {}", this)
        store.dispose()
        disposables.dispose()
    }

    internal fun postAction(action: A) {
        store.postAction(action)
    }
}
