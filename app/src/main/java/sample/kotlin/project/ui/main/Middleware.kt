package sample.kotlin.project.ui.main

import io.reactivex.Observable

interface Middleware<A : Action, S : State> {

    fun bind(actions: Observable<A>, state: Observable<S>): Observable<A>
}
