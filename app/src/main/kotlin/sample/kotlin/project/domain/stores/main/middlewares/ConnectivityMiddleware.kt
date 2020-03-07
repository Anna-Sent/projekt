package sample.kotlin.project.domain.stores.main.middlewares

import io.reactivex.Observable
import io.reactivex.functions.Consumer
import sample.kotlin.project.domain.core.mvi.BaseMiddleware
import sample.kotlin.project.domain.repositories.connectivity.ConnectivityRepository
import sample.kotlin.project.domain.stores.main.pojo.MainAction
import sample.kotlin.project.domain.stores.main.pojo.MainAction.OnConnectivityChanged
import sample.kotlin.project.domain.stores.main.pojo.MainEvent
import sample.kotlin.project.domain.stores.main.pojo.MainNavigationCommand
import sample.kotlin.project.domain.stores.main.pojo.MainState
import javax.inject.Inject

class ConnectivityMiddleware
@Inject constructor(
    private val connectivityRepository: ConnectivityRepository
) : BaseMiddleware<MainState, MainAction, MainEvent, MainNavigationCommand>() {

    override fun bind(
        states: Observable<MainState>,
        actions: Observable<MainAction>,
        events: Consumer<MainEvent>,
        navigationCommands: Consumer<MainNavigationCommand>
    ): Observable<MainAction> =
        actions
            .ofType(OnConnectivityChanged::class.java)
            .doOnNext { connectivityRepository.setNetworkConnected(it.isConnected) }
            .switchMap { Observable.never<MainAction>() }
}
