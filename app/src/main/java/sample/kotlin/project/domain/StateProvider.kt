package sample.kotlin.project.domain

import sample.kotlin.project.domain.mvi.State

interface StateProvider<S : State> {

    fun get(): S
}
