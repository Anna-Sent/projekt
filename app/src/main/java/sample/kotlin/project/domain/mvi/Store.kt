package sample.kotlin.project.domain.mvi

import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.withLatestFrom

open class Store<A : Action, S : State, E : Event>(
    private val reducer: Reducer<S, A>,
    private val middlewares: Set<Middleware<A, S, E>>,
    initialState: S
) {

    private val uiScheduler = AndroidSchedulers.mainThread()

    private val states = BehaviorRelay.createDefault<S>(initialState).toSerialized()
    private val actions = PublishRelay.create<A>().toSerialized()
    private val events = PublishRelay.create<E>().toSerialized()

    fun wire(): Disposable {
        val disposable = CompositeDisposable()

        disposable += actions
            .withLatestFrom(states) { action: A, state: S ->
                reducer.reduce(state, action)
            }
            .distinctUntilChanged()
            .subscribe(states::accept)

        disposable += Observable.merge<A>(
            middlewares.map { it.bind(actions, states, events) }
        )
            .subscribe(actions::accept)

        return disposable
    }

    fun bind(view: MviView<A, S, E>): Disposable {
        val disposable = CompositeDisposable()

        disposable += states.observeOn(uiScheduler).subscribe(view::render)
        disposable += view.actions.subscribe(actions::accept)
        disposable += events.observeOn(uiScheduler).subscribe { view.eventsConsumer.accept(it) }

        return disposable
    }
}
