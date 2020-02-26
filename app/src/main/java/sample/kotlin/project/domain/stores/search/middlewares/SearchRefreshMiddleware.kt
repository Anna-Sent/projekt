package sample.kotlin.project.domain.stores.search.middlewares

import io.reactivex.Observable
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.withLatestFrom
import sample.kotlin.project.domain.core.mvi.BaseMiddleware
import sample.kotlin.project.domain.repositories.search.SearchRequest
import sample.kotlin.project.domain.stores.search.pojo.*
import javax.inject.Inject

class SearchRefreshMiddleware
@Inject constructor(
) : BaseMiddleware<SearchState, SearchAction, SearchEvent, SearchNavigationCommand>() {

    override fun bind(
        states: Observable<SearchState>,
        actions: Observable<SearchAction>,
        events: Consumer<SearchEvent>,
        navigationCommands: Consumer<SearchNavigationCommand>
    ): Observable<SearchAction> =
        actions
            .ofType<SearchAction.OnRefresh>(
                SearchAction.OnRefresh::class.java
            )
            .withLatestFrom(states) { _, state ->
                SearchRequest(state.lastQuery, 1)
            }
            .map { SearchAction.LoadSearchResults(it, LoadingStatus.FIRST_PAGE_REFRESH) }
}
