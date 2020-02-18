package sample.kotlin.project.presentation.activities.main.state

import sample.kotlin.project.domain.core.Mapper
import sample.kotlin.project.domain.stores.main.data.MainState
import javax.inject.Inject

class MainStateFromParcelableMapper
@Inject constructor(
) : Mapper<MainStateParcelable, MainState> {

    override fun map(from: MainStateParcelable) = MainState
}
