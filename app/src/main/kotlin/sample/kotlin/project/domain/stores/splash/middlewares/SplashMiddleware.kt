package sample.kotlin.project.domain.stores.splash.middlewares

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.Consumer
import sample.kotlin.project.domain.core.mvi.BaseMiddleware
import sample.kotlin.project.domain.providers.schedulers.SchedulersProvider
import sample.kotlin.project.domain.stores.splash.pojo.SplashAction
import sample.kotlin.project.domain.stores.splash.pojo.SplashEvent
import sample.kotlin.project.domain.stores.splash.pojo.SplashNavigationCommand
import sample.kotlin.project.domain.stores.splash.pojo.SplashNavigationCommand.NavigateToSearchScreen
import sample.kotlin.project.domain.stores.splash.pojo.SplashState
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SplashMiddleware
@Inject constructor(
    private val schedulersProvider: SchedulersProvider
) : BaseMiddleware<SplashState, SplashAction, SplashEvent, SplashNavigationCommand>() {

    override fun bind(
        states: Observable<SplashState>,
        actions: Observable<SplashAction>,
        events: Consumer<SplashEvent>,
        navigationCommands: Consumer<SplashNavigationCommand>
    ): Observable<SplashAction> =
        Single.timer(2, TimeUnit.SECONDS, schedulersProvider.ioScheduler)
            .doOnSuccess { navigationCommands.accept(NavigateToSearchScreen) }
            .flatMapObservable { Observable.never<SplashAction>() }
}
