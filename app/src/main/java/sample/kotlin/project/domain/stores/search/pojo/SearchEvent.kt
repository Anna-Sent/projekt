package sample.kotlin.project.domain.stores.search.pojo

import sample.kotlin.project.domain.core.mvi.pojo.Event

sealed class SearchEvent : Event {

    data class SearchRefreshFailureEvent(val error: Throwable) : SearchEvent()
}
