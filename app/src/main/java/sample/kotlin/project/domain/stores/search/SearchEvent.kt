package sample.kotlin.project.domain.stores.search

import sample.kotlin.project.domain.core.mvi.Event

sealed class SearchEvent : Event {

    class SearchFailureEvent(val error: Throwable) : SearchEvent()
}
