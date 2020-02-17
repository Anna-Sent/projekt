package sample.kotlin.project.domain.core.mvi

import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import io.logging.LogSystem
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.withLatestFrom
import org.slf4j.LoggerFactory

open class Store<A : Action, S : State, E : Event>(
    reducer: Reducer<S, A>,
    middlewares: Set<Middleware<A, S, E>>,
    initialState: S
) {

    final override fun toString() = super.toString()
    private val logger = LoggerFactory.getLogger(toString())
    private fun unexpectedError(throwable: Throwable) {
        logger.error("Unexpected error occurred", throwable)
        LogSystem.report(logger, "Unexpected error occurred", throwable)
    }

    private val disposables = CompositeDisposable()

    private val states = BehaviorRelay.createDefault<S>(initialState).toSerialized()
    private val events = PublishRelay.create<E>().toSerialized()
    private val actions = PublishRelay.create<A>().toSerialized()

    val statesObservable: Observable<S> = states.hide()
    val eventsObservable: Observable<E> = events.hide()

    init {
        disposables += actions
            .withLatestFrom(states) { action: A, state: S ->
                reducer.reduce(state, action)
            }
            .distinctUntilChanged()
            .subscribe(states::accept, ::unexpectedError)

        disposables += Observable.merge<A>(
            middlewares.map { it.bind(actions, states, events) }
        )
            .subscribe(actions::accept, ::unexpectedError)
    }

    fun dispose() {
        logger.debug("dispose")
        disposables.dispose()
    }

    fun postAction(action: A) {
        logger.debug("post action {}", action)
        actions.accept(action)
    }
}
