package sample.kotlin.project.presentation.activities.main.state

import sample.kotlin.project.domain.core.mappers.Mapper
import sample.kotlin.project.domain.stores.main.pojo.MainState
import javax.inject.Inject

class MainStateToParcelableMapper
@Inject constructor(
) : Mapper<MainState, MainStateParcelable> {

    override fun map(from: MainState) = MainStateParcelable
}
