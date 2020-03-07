package sample.kotlin.project.domain.core.mvi

import io.reactivex.Observable
import io.reactivex.functions.Consumer
import sample.kotlin.project.domain.core.mvi.pojo.Action
import sample.kotlin.project.domain.core.mvi.pojo.Event
import sample.kotlin.project.domain.core.mvi.pojo.NavigationCommand
import sample.kotlin.project.domain.core.mvi.pojo.State

interface Middleware<S : State, A : Action, E : Event, NC : NavigationCommand> {

    fun bind(
        states: Observable<S>,
        actions: Observable<A>,
        events: Consumer<E>,
        navigationCommands: Consumer<NC>
    ): Observable<A>
}
