package sample.kotlin.project.domain.stores.search.entities

import sample.kotlin.project.domain.core.mvi.entities.Event

sealed class SearchEvent : Event {

    class SearchFailureEvent(val error: Throwable) : SearchEvent()
}
