package sample.kotlin.project.domain.core.mvi

import sample.kotlin.project.domain.core.mvi.pojo.Event
import sample.kotlin.project.domain.core.mvi.pojo.State
import java.util.*

class EventsHolder<S : State, E : Event> {

    private var view: MviView<S, E>? = null
    private val pendingEvents = LinkedList<E>()

    fun attachView(view: MviView<S, E>?) {
        this.view = view
        view?.let {
            while (!pendingEvents.isEmpty()) {
                it.handle(pendingEvents.poll()!!)
            }
        }
    }

    fun detachView() {
        view = null
    }

    fun handle(event: E) {
        val view = this.view
        view?.handle(event) ?: pendingEvents.add(event)
    }
}
