package sample.kotlin.project.domain.stores.splash

import sample.kotlin.project.domain.core.mvi.Reducer
import sample.kotlin.project.domain.stores.splash.entities.SplashAction
import sample.kotlin.project.domain.stores.splash.entities.SplashState

internal class SplashReducer : Reducer<SplashState, SplashAction> {

    override fun reduce(state: SplashState, action: SplashAction) = state
}
