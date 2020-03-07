package sample.kotlin.project.domain.stores.search.middlewares

import io.reactivex.Observable
import io.reactivex.functions.Consumer
import sample.kotlin.project.domain.core.mvi.BaseMiddleware
import sample.kotlin.project.domain.repositories.search.SearchRequest
import sample.kotlin.project.domain.stores.search.pojo.SearchAction
import sample.kotlin.project.domain.stores.search.pojo.SearchAction.LoadSearchResults
import sample.kotlin.project.domain.stores.search.pojo.SearchAction.OnSearchClick
import sample.kotlin.project.domain.stores.search.pojo.SearchEvent
import sample.kotlin.project.domain.stores.search.pojo.SearchNavigationCommand
import sample.kotlin.project.domain.stores.search.pojo.SearchRequestType.FIRST_PAGE
import sample.kotlin.project.domain.stores.search.pojo.SearchState
import javax.inject.Inject

class SearchFirstPageMiddleware
@Inject constructor() :
    BaseMiddleware<SearchState, SearchAction, SearchEvent, SearchNavigationCommand>() {

    override fun bind(
        states: Observable<SearchState>,
        actions: Observable<SearchAction>,
        events: Consumer<SearchEvent>,
        navigationCommands: Consumer<SearchNavigationCommand>
    ): Observable<SearchAction> =
        actions
            .ofType(OnSearchClick::class.java)
            .map { LoadSearchResults(SearchRequest(it.query, 1), FIRST_PAGE) }
}
