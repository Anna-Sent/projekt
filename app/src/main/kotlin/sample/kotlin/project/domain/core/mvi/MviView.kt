package sample.kotlin.project.domain.core.mvi

import sample.kotlin.project.domain.core.mvi.pojo.Event
import sample.kotlin.project.domain.core.mvi.pojo.State

interface MviView<S : State, E : Event> {

    fun render(state: S)

    fun handle(event: E)
}
