package sample.kotlin.project.presentation.fragments.splash.state

import sample.kotlin.project.domain.core.Mapper
import sample.kotlin.project.domain.stores.splash.entities.SplashState
import javax.inject.Inject

class SplashStateFromParcelableMapper
@Inject constructor(
) : Mapper<SplashStateParcelable, SplashState> {

    override fun map(from: SplashStateParcelable) = SplashState
}
