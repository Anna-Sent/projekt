package sample.kotlin.project.domain.core.mvi

import sample.kotlin.project.domain.core.mvi.entities.Action
import sample.kotlin.project.domain.core.mvi.entities.State

interface Reducer<S : State, A : Action> {

    fun reduce(state: S, action: A): S
}
