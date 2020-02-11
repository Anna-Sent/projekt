package sample.kotlin.project.ui.main

import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.withLatestFrom

open class Store<A : Action, S : State>(
    private val reducer: Reducer<S, A>,
    private val middlewares: List<Middleware<A, S>>,
    initialState: S
) {

    private val uiScheduler = AndroidSchedulers.mainThread()

    private val state: BehaviorRelay<S> = BehaviorRelay.createDefault<S>(initialState)
    private val actions: PublishRelay<A> = PublishRelay.create<A>()

    fun wire(): Disposable {
        val disposable = CompositeDisposable()

        disposable += actions
            .withLatestFrom(state) { action: A, state: S ->
                reducer.reduce(state, action)
            }
            .distinctUntilChanged()
            .subscribe(state::accept)

        disposable += Observable.merge<A>(
            middlewares.map { middleware -> middleware.bind(actions, state) }
        )
            .subscribe(actions::accept)

        return disposable
    }

    fun bind(view: MviView<A, S>): Disposable {
        val disposable = CompositeDisposable()

        disposable += state.observeOn(uiScheduler).subscribe(view::render)
        disposable += view.userActions.subscribe(actions::accept)

        return disposable
    }
}
