package sample.kotlin.project.domain.stores.search.middlewares

import io.reactivex.Observable
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.withLatestFrom
import sample.kotlin.project.domain.core.mvi.BaseMiddleware
import sample.kotlin.project.domain.repositories.search.SearchRequest
import sample.kotlin.project.domain.stores.search.pojo.*
import javax.inject.Inject

class SearchNextPageRetryMiddleware
@Inject constructor(
) : BaseMiddleware<SearchState, SearchAction, SearchEvent, SearchNavigationCommand>() {

    override fun bind(
        states: Observable<SearchState>,
        actions: Observable<SearchAction>,
        events: Consumer<SearchEvent>,
        navigationCommands: Consumer<SearchNavigationCommand>
    ): Observable<SearchAction> =
        actions
            .ofType<SearchAction.OnRetryNextPageClick>(
                SearchAction.OnRetryNextPageClick::class.java
            )
            .withLatestFrom(states) { _, state ->
                state to SearchRequest(state.lastQuery, state.nextPage)
            }
            .flatMap {
                if (it.first.requestType == null)
                    Observable.just(
                        SearchAction.LoadSearchResults(
                            it.second,
                            SearchRequestType.NEXT_PAGE
                        )
                    )
                else Observable.never<SearchAction>()
            }
}
