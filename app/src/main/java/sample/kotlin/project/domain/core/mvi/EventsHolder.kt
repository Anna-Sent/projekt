package sample.kotlin.project.domain.core.mvi

import sample.kotlin.project.domain.core.mvi.entities.Event
import sample.kotlin.project.domain.core.mvi.entities.State
import java.util.*

class EventsHolder<S : State, E : Event> {

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
