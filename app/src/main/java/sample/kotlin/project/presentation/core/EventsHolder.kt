package sample.kotlin.project.presentation.core

import sample.kotlin.project.domain.core.mvi.Event
import sample.kotlin.project.domain.core.mvi.MviView
import sample.kotlin.project.domain.core.mvi.State
import java.util.*

internal class EventsHolder<S : State, E : Event> {

    private var view: MviView<S, E>? = null
    private val pendingEvents = LinkedList<E>()

    fun attachView(view: MviView<S, E>?) {
        this.view = view
        if (view != null) {
            while (!pendingEvents.isEmpty()) {
                view.handleEvent(pendingEvents.poll()!!)
            }
        }
    }

    fun detachView() {
        view = null
    }

    fun handleEvent(event: E) {
        val view = this.view
        if (view != null) {
            view.handleEvent(event)
        } else {
            pendingEvents.add(event)
        }
    }
}
