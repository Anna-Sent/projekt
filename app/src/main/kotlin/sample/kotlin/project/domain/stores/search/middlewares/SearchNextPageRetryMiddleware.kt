package sample.kotlin.project.domain.stores.search.middlewares

import io.reactivex.Observable
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.withLatestFrom
import sample.kotlin.project.domain.core.mvi.BaseMiddleware
import sample.kotlin.project.domain.repositories.search.SearchRequest
import sample.kotlin.project.domain.stores.search.pojo.SearchAction
import sample.kotlin.project.domain.stores.search.pojo.SearchAction.LoadSearchResults
import sample.kotlin.project.domain.stores.search.pojo.SearchAction.OnRetryNextPageClick
import sample.kotlin.project.domain.stores.search.pojo.SearchEvent
import sample.kotlin.project.domain.stores.search.pojo.SearchNavigationCommand
import sample.kotlin.project.domain.stores.search.pojo.SearchRequestType.NEXT_PAGE
import sample.kotlin.project.domain.stores.search.pojo.SearchState
import javax.inject.Inject

class SearchNextPageRetryMiddleware
@Inject constructor() :
    BaseMiddleware<SearchState, SearchAction, SearchEvent, SearchNavigationCommand>() {

    override fun bind(
        states: Observable<SearchState>,
        actions: Observable<SearchAction>,
        events: Consumer<SearchEvent>,
        navigationCommands: Consumer<SearchNavigationCommand>
    ): Observable<SearchAction> =
        actions
            .ofType(OnRetryNextPageClick::class.java)
            .withLatestFrom(states) { _, state ->
                state to SearchRequest(state.lastQuery!!, state.nextPage)
            }
            .flatMap { (state, request) ->
                if (state.requestType == null && state.nextPage > 0) {
                    Observable.just(LoadSearchResults(request, NEXT_PAGE))
                } else {
                    Observable.never()
                }
            }
}
