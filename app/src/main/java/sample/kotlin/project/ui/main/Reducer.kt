package sample.kotlin.project.ui.main

interface Reducer<S : State, in A : Action> {

    fun reduce(state: S, action: A): S
}
