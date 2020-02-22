package sample.kotlin.project.presentation.fragments.splash.state

import sample.kotlin.project.domain.core.mappers.Mapper
import sample.kotlin.project.domain.stores.splash.pojo.SplashState
import javax.inject.Inject

class SplashStateToParcelableMapper
@Inject constructor(
) : Mapper<SplashState, SplashStateParcelable> {

    override fun map(from: SplashState) = SplashStateParcelable
}
