package sample.kotlin.project.presentation.activities.main

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import sample.kotlin.project.domain.core.mappers.Mapper
import sample.kotlin.project.domain.stores.main.pojo.MainState
import javax.inject.Inject

@Parcelize
object MainStateParcelable : Parcelable

class MainStateToParcelableMapper
@Inject constructor() : Mapper<MainState, MainStateParcelable> {

    override fun map(from: MainState) = MainStateParcelable
}

class MainStateFromParcelableMapper
@Inject constructor() : Mapper<MainStateParcelable, MainState> {

    override fun map(from: MainStateParcelable) = MainState
}
