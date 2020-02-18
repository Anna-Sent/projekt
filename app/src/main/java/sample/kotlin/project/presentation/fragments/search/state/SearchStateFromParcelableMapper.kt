package sample.kotlin.project.presentation.fragments.search.state

import sample.kotlin.project.domain.core.Mapper
import sample.kotlin.project.domain.stores.search.data.SearchState
import javax.inject.Inject

class SearchStateFromParcelableMapper
@Inject constructor(
) : Mapper<SearchStateParcelable, SearchState> {

    override fun map(from: SearchStateParcelable) =
        SearchState(
            loading = from.loading,
            data = from.data,
            suggestions = from.suggestions
        )
}
