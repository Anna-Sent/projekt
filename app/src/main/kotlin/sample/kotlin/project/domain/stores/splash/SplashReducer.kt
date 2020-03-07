package sample.kotlin.project.domain.stores.splash

import sample.kotlin.project.domain.core.mvi.Reducer
import sample.kotlin.project.domain.stores.splash.pojo.SplashAction
import sample.kotlin.project.domain.stores.splash.pojo.SplashState

internal class SplashReducer : Reducer<SplashState, SplashAction> {

    override fun reduce(action: SplashAction, state: SplashState) = state
}
