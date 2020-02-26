package sample.kotlin.project.presentation.fragments.splash

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import sample.kotlin.project.domain.core.mappers.Mapper
import sample.kotlin.project.domain.stores.splash.pojo.SplashState
import javax.inject.Inject

@Parcelize
object SplashStateParcelable : Parcelable

class SplashStateToParcelableMapper
@Inject constructor(
) : Mapper<SplashState, SplashStateParcelable> {

    override fun map(from: SplashState) = SplashStateParcelable
}

class SplashStateFromParcelableMapper
@Inject constructor(
) : Mapper<SplashStateParcelable, SplashState> {

    override fun map(from: SplashStateParcelable) = SplashState
}
