package sample.kotlin.project.domain.stores.search.middlewares

import io.reactivex.Observable
import io.reactivex.functions.Consumer
import sample.kotlin.project.domain.core.mvi.BaseMiddleware
import sample.kotlin.project.domain.repositories.search.SearchRepository
import sample.kotlin.project.domain.stores.search.entities.SearchAction
import sample.kotlin.project.domain.stores.search.entities.SearchEvent
import sample.kotlin.project.domain.stores.search.entities.SearchNavigationCommand
import sample.kotlin.project.domain.stores.search.entities.SearchState
import javax.inject.Inject

class SearchMiddleware
@Inject constructor(
    private val searchRepository: SearchRepository
) : BaseMiddleware<SearchState, SearchAction, SearchEvent, SearchNavigationCommand>() {

    override fun bind(
        states: Observable<SearchState>,
        actions: Observable<SearchAction>,
        events: Consumer<SearchEvent>,
        navigationCommands: Consumer<SearchNavigationCommand>
    ): Observable<SearchAction> =
        actions
            .ofType<SearchAction.SearchClickAction>(
                SearchAction.SearchClickAction::class.java
            )
            .switchMap {
                searchRepository.search(it.query)
                    .toObservable()
                    .map<SearchAction> { SearchAction.SearchSuccessAction(it) }
                    .doOnError { events.accept(SearchEvent.SearchFailureEvent(it)) }
                    .onErrorReturn { SearchAction.SearchFailureAction(it) }
            }
}
