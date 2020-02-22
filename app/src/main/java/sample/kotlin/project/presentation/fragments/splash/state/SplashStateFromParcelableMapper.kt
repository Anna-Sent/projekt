package sample.kotlin.project.presentation.fragments.splash.state

import sample.kotlin.project.domain.core.mappers.Mapper
import sample.kotlin.project.domain.stores.splash.pojo.SplashState
import javax.inject.Inject

class SplashStateFromParcelableMapper
@Inject constructor(
) : Mapper<SplashStateParcelable, SplashState> {

    override fun map(from: SplashStateParcelable) = SplashState
}
