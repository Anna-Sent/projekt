package sample.kotlin.project.domain.stores.search.middlewares

import io.reactivex.Observable
import io.reactivex.functions.Consumer
import sample.kotlin.project.domain.core.mvi.Middleware
import sample.kotlin.project.domain.sources.common.RequestStatusLocalSource
import sample.kotlin.project.domain.sources.common.RequestType
import sample.kotlin.project.domain.stores.search.entities.SearchAction
import sample.kotlin.project.domain.stores.search.entities.SearchEvent
import sample.kotlin.project.domain.stores.search.entities.SearchNavigationCommand
import sample.kotlin.project.domain.stores.search.entities.SearchState
import javax.inject.Inject

class StateMiddleware
@Inject constructor(
    private val requestStatusLocalSource: RequestStatusLocalSource
) : Middleware<SearchState, SearchAction, SearchEvent, SearchNavigationCommand> {

    override fun bind(
        states: Observable<SearchState>,
        actions: Observable<SearchAction>,
        events: Consumer<SearchEvent>,
        navigationCommands: Consumer<SearchNavigationCommand>
    ): Observable<SearchAction> =
        requestStatusLocalSource.status(RequestType.Search)
            .map {
                if (it) SearchAction.SearchLoadingStartedAction
                else SearchAction.SearchLoadingFinishedAction
            }
}
