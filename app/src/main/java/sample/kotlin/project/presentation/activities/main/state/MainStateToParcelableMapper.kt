package sample.kotlin.project.presentation.activities.main.state

import sample.kotlin.project.domain.core.Mapper
import sample.kotlin.project.domain.stores.main.data.MainState
import javax.inject.Inject

class MainStateToParcelableMapper
@Inject constructor(
) : Mapper<MainState, MainStateParcelable> {

    override fun map(from: MainState) = MainStateParcelable
}
