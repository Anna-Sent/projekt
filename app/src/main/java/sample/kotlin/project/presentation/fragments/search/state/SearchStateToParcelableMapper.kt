package sample.kotlin.project.presentation.fragments.search.state

import sample.kotlin.project.domain.core.mappers.Mapper
import sample.kotlin.project.domain.stores.search.pojo.SearchState
import javax.inject.Inject

class SearchStateToParcelableMapper
@Inject constructor(
) : Mapper<SearchState, SearchStateParcelable> {

    override fun map(from: SearchState) =
        SearchStateParcelable(
            loading = from.loading,
            suggestions = from.suggestions
        )
}
