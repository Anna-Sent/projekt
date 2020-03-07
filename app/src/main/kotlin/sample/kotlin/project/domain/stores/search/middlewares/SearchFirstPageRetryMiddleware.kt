package sample.kotlin.project.domain.stores.search.middlewares

import io.reactivex.Observable
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.withLatestFrom
import sample.kotlin.project.domain.core.mvi.BaseMiddleware
import sample.kotlin.project.domain.repositories.search.SearchRequest
import sample.kotlin.project.domain.stores.search.pojo.SearchAction
import sample.kotlin.project.domain.stores.search.pojo.SearchAction.LoadSearchResults
import sample.kotlin.project.domain.stores.search.pojo.SearchAction.OnRetryClick
import sample.kotlin.project.domain.stores.search.pojo.SearchEvent
import sample.kotlin.project.domain.stores.search.pojo.SearchNavigationCommand
import sample.kotlin.project.domain.stores.search.pojo.SearchRequestType.FIRST_PAGE_RETRY
import sample.kotlin.project.domain.stores.search.pojo.SearchState
import javax.inject.Inject

class SearchFirstPageRetryMiddleware
@Inject constructor() :
    BaseMiddleware<SearchState, SearchAction, SearchEvent, SearchNavigationCommand>() {

    override fun bind(
        states: Observable<SearchState>,
        actions: Observable<SearchAction>,
        events: Consumer<SearchEvent>,
        navigationCommands: Consumer<SearchNavigationCommand>
    ): Observable<SearchAction> =
        actions
            .ofType(OnRetryClick::class.java)
            .withLatestFrom(states) { _, state ->
                SearchRequest(state.lastQuery!!, 1)
            }
            .map { LoadSearchResults(it, FIRST_PAGE_RETRY) }
}
