package sample.kotlin.project.domain.stores.splash.middlewares

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.Consumer
import sample.kotlin.project.domain.core.mvi.BaseMiddleware
import sample.kotlin.project.domain.stores.splash.pojo.SplashAction
import sample.kotlin.project.domain.stores.splash.pojo.SplashEvent
import sample.kotlin.project.domain.stores.splash.pojo.SplashNavigationCommand
import sample.kotlin.project.domain.stores.splash.pojo.SplashState
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SplashMiddleware
@Inject constructor(
) : BaseMiddleware<SplashState, SplashAction, SplashEvent, SplashNavigationCommand>() {

    override fun bind(
        states: Observable<SplashState>,
        actions: Observable<SplashAction>,
        events: Consumer<SplashEvent>,
        navigationCommands: Consumer<SplashNavigationCommand>
    ): Observable<SplashAction> =
        Single.timer(2, TimeUnit.SECONDS)
            .doOnSuccess { navigationCommands.accept(SplashNavigationCommand.NavigateToSearchScreen) }
            .flatMapObservable { Observable.never<SplashAction>() }
}
