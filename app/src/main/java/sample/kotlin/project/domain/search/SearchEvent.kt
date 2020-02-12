package sample.kotlin.project.domain.search

import sample.kotlin.project.domain.mvi.Event

sealed class SearchEvent : Event {

    class SearchFailureEvent(val throwable: Throwable) : SearchEvent()
}
