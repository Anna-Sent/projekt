package sample.kotlin.project.domain.core.mvi

import sample.kotlin.project.domain.core.mvi.entities.Event
import sample.kotlin.project.domain.core.mvi.entities.State

interface MviView<S : State, E : Event> {

    fun render(state: S)

    fun handleEvent(event: E)
}
