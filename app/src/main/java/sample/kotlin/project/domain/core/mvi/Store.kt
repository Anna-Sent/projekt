package sample.kotlin.project.domain.core.mvi

import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import io.logging.LogSystem
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.withLatestFrom
import org.slf4j.LoggerFactory
import sample.kotlin.project.domain.core.mvi.pojo.*
import sample.kotlin.project.domain.providers.schedulers.SchedulersProvider

open class Store<S : State, A : Action, E : Event, NC : NavigationCommand>(
    schedulersProvider: SchedulersProvider,
    reducer: Reducer<S, A>,
    middlewares: Set<Middleware<S, A, E, NC>>,
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
    private val actions = PublishRelay.create<A>().toSerialized()
    private val events = PublishRelay.create<E>().toSerialized()
    private val navigationCommands = PublishRelay.create<NC>()

    val statesObservable: Observable<S> = states.hide()
    val eventsHolder = EventsHolder<S, E>()
    val navigationCommandsObservable: Observable<NC> = navigationCommands.hide()

    init {
        disposables += actions
            .withLatestFrom(states, reducer::reduce)
            .distinctUntilChanged()
            .subscribe(states::accept, ::unexpectedError)

        disposables += Observable.merge<A>(
            middlewares.map { it.bind(states, actions, events, navigationCommands) }
        )
            .subscribe(actions::accept, ::unexpectedError)

        disposables += events
            .observeOn(schedulersProvider.uiScheduler)
            .subscribe(eventsHolder::handle, ::unexpectedError)
    }

    fun dispose() {
        logger.debug("dispose")
        disposables.dispose()
    }

    fun dispatch(action: A) {
        logger.debug("dispatch action {}", action)
        actions.accept(action)
    }
}
