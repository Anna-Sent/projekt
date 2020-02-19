package sample.kotlin.project.domain.stores.search.middlewares

import io.reactivex.Observable
import io.reactivex.functions.Consumer
import sample.kotlin.project.domain.core.mvi.Middleware
import sample.kotlin.project.domain.sources.search.SearchSource
import sample.kotlin.project.domain.stores.search.entities.SearchAction
import sample.kotlin.project.domain.stores.search.entities.SearchEvent
import sample.kotlin.project.domain.stores.search.entities.SearchNavigationCommand
import sample.kotlin.project.domain.stores.search.entities.SearchState
import javax.inject.Inject

class SuggestionsMiddleware
@Inject constructor(
    private val searchSource: SearchSource
) : Middleware<SearchState, SearchAction, SearchEvent, SearchNavigationCommand> {

    override fun bind(
        states: Observable<SearchState>,
        actions: Observable<SearchAction>,
        events: Consumer<SearchEvent>,
        navigationCommands: Consumer<SearchNavigationCommand>
    ): Observable<SearchAction> =
        actions
            .ofType<SearchAction.LoadSuggestionsAction>(
                SearchAction.LoadSuggestionsAction::class.java
            )
            .switchMap {
                searchSource.suggestions()
                    .toObservable()
                    .onErrorReturnItem(emptyList())
                    .map { SearchAction.SuggestionsLoadedAction(it) }
            }
}
