package sample.kotlin.project.domain.core.mvi

import sample.kotlin.project.domain.core.mvi.pojo.Action
import sample.kotlin.project.domain.core.mvi.pojo.State

interface Reducer<S : State, A : Action> {

    fun reduce(action: A, state: S): S
}
