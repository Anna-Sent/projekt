package sample.kotlin.project.domain.stores.search.middlewares

import io.reactivex.Observable
import io.reactivex.functions.Consumer
import sample.kotlin.project.domain.core.mvi.BaseMiddleware
import sample.kotlin.project.domain.repositories.search.SearchRequest
import sample.kotlin.project.domain.stores.search.pojo.*
import javax.inject.Inject

class SearchFirstPageMiddleware
@Inject constructor(
) : BaseMiddleware<SearchState, SearchAction, SearchEvent, SearchNavigationCommand>() {

    override fun bind(
        states: Observable<SearchState>,
        actions: Observable<SearchAction>,
        events: Consumer<SearchEvent>,
        navigationCommands: Consumer<SearchNavigationCommand>
    ): Observable<SearchAction> =
        actions
            .ofType<SearchAction.OnSearchClick>(
                SearchAction.OnSearchClick::class.java
            )
            .map {
                SearchAction.LoadSearchResults(
                    SearchRequest(it.query, 1),
                    LoadingStatus.FIRST_PAGE_INITIAL
                )
            }
}
