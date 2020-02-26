package sample.kotlin.project.domain.stores.search.middlewares

import io.reactivex.Observable
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.withLatestFrom
import sample.kotlin.project.domain.core.mvi.BaseMiddleware
import sample.kotlin.project.domain.repositories.search.SearchRequest
import sample.kotlin.project.domain.stores.search.pojo.*
import javax.inject.Inject

class SearchNextPageMiddleware
@Inject constructor(
) : BaseMiddleware<SearchState, SearchAction, SearchEvent, SearchNavigationCommand>() {

    override fun bind(
        states: Observable<SearchState>,
        actions: Observable<SearchAction>,
        events: Consumer<SearchEvent>,
        navigationCommands: Consumer<SearchNavigationCommand>
    ): Observable<SearchAction> =
        actions
            .ofType<SearchAction.OnScrolledToBottom>(
                SearchAction.OnScrolledToBottom::class.java
            )
            .withLatestFrom(states) { _, state ->
                SearchRequest(state.lastQuery, state.lastLoadedPage + 1)
            }
            .map { SearchAction.LoadSearchResults(it, LoadingStatus.NEXT_PAGE) }
}
