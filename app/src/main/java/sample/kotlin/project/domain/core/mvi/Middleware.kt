package sample.kotlin.project.domain.core.mvi

import io.reactivex.Observable
import io.reactivex.functions.Consumer
import sample.kotlin.project.domain.core.mvi.entities.Action
import sample.kotlin.project.domain.core.mvi.entities.Event
import sample.kotlin.project.domain.core.mvi.entities.NavigationCommand
import sample.kotlin.project.domain.core.mvi.entities.State

interface Middleware<S : State, A : Action, E : Event, NC : NavigationCommand> {

    fun bind(
        states: Observable<S>,
        actions: Observable<A>,
        events: Consumer<E>,
        navigationCommands: Consumer<NC>
    ): Observable<A>
}
