package sample.kotlin.project.domain.stores.search.middlewares

import io.reactivex.Observable
import io.reactivex.functions.Consumer
import sample.kotlin.project.domain.core.mvi.Middleware
import sample.kotlin.project.domain.network.NetworkConnectivityHelper
import sample.kotlin.project.domain.stores.search.data.SearchAction
import sample.kotlin.project.domain.stores.search.data.SearchEvent
import sample.kotlin.project.domain.stores.search.data.SearchState
import javax.inject.Inject

class NetworkConnectivityMiddleware
@Inject constructor(
    private val networkConnectivityHelper: NetworkConnectivityHelper
) : Middleware<SearchAction, SearchState, SearchEvent> {

    override fun bind(
        actions: Observable<SearchAction>,
        states: Observable<SearchState>,
        events: Consumer<SearchEvent>
    ): Observable<SearchAction> =
        networkConnectivityHelper.isNetworkConnected()
            .map { SearchAction.NetworkConnectedAction(it) }
}
