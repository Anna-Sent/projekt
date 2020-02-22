package sample.kotlin.project.domain.core.mvi

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import sample.kotlin.project.domain.core.mvi.pojo.Action
import sample.kotlin.project.domain.core.mvi.pojo.Event
import sample.kotlin.project.domain.core.mvi.pojo.NavigationCommand
import sample.kotlin.project.domain.core.mvi.pojo.State

abstract class BaseMiddleware<S : State, A : Action, E : Event, NC : NavigationCommand> :
    Middleware<S, A, E, NC> {

    final override fun toString() = super.toString()
    protected val logger: Logger = LoggerFactory.getLogger(toString())
}
