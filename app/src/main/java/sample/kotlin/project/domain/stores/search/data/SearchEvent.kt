package sample.kotlin.project.domain.stores.search.data

import sample.kotlin.project.domain.core.mvi.Event

sealed class SearchEvent : Event {

    class SearchFailureEvent(val error: Throwable) : SearchEvent()
}
