package sample.kotlin.project.domain.stores.search.middlewares

import io.reactivex.Observable
import io.reactivex.functions.Consumer
import sample.kotlin.project.domain.core.mvi.Middleware
import sample.kotlin.project.domain.sources.common.RequestStatusLocalSource
import sample.kotlin.project.domain.sources.common.RequestType
import sample.kotlin.project.domain.stores.search.SearchAction
import sample.kotlin.project.domain.stores.search.SearchEvent
import sample.kotlin.project.domain.stores.search.SearchState
import javax.inject.Inject

class StateMiddleware
@Inject constructor(
    private val requestStatusLocalSource: RequestStatusLocalSource
) : Middleware<SearchAction, SearchState, SearchEvent> {

    override fun bind(
        actions: Observable<SearchAction>,
        states: Observable<SearchState>,
        events: Consumer<SearchEvent>
    ): Observable<SearchAction> =
        requestStatusLocalSource.status(RequestType.Search)
            .map {
                if (it) SearchAction.SearchLoadingStartedAction
                else SearchAction.SearchLoadingFinishedAction
            }
}
