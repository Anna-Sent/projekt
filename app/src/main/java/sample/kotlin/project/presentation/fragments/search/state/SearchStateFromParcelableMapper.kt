package sample.kotlin.project.presentation.fragments.search.state

import sample.kotlin.project.domain.core.mappers.Mapper
import sample.kotlin.project.domain.stores.search.pojo.SearchState
import javax.inject.Inject

class SearchStateFromParcelableMapper
@Inject constructor(
) : Mapper<SearchStateParcelable, SearchState> {

    override fun map(from: SearchStateParcelable) =
        SearchState(
            loading = from.loading,
            suggestions = from.suggestions
        )
}
