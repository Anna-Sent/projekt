package sample.kotlin.project.presentation.core.views

import androidx.lifecycle.ViewModel
import io.logging.LogSystem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import org.slf4j.LoggerFactory
import sample.kotlin.project.domain.core.mvi.Store
import sample.kotlin.project.domain.core.mvi.entities.Action
import sample.kotlin.project.domain.core.mvi.entities.Event
import sample.kotlin.project.domain.core.mvi.entities.NavigationCommand
import sample.kotlin.project.domain.core.mvi.entities.State

abstract class BaseViewModel<S : State, A : Action, E : Event, NC : NavigationCommand>
constructor(
    private val store: Store<S, A, E, NC>
) : ViewModel() {

    final override fun toString() = super.toString()
    protected val logger = LoggerFactory.getLogger(toString())
    protected fun unexpectedError(throwable: Throwable) {
        logger.error("Unexpected error occurred", throwable)
        LogSystem.report(logger, "Unexpected error occurred", throwable)
    }

    internal val statesObservable = store.statesObservable
    internal val eventsHolder = store.eventsHolder
    private val navigationCommandsObservable = store.navigationCommandsObservable
    private val disposables = CompositeDisposable()

    init {
        disposables += navigationCommandsObservable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::handleNavigationCommand, ::unexpectedError)
    }

    protected open fun handleNavigationCommand(navigationCommand: NC) {
        // override in nested classes if needed
    }

    override fun onCleared() {
        logger.debug("cleared view model {}", this)
        store.dispose()
        disposables.clear()
    }

    internal fun postAction(action: A) {
        store.postAction(action)
    }
}
