package sample.kotlin.project.presentation.fragments.search

import sample.kotlin.project.domain.Mapper
import sample.kotlin.project.domain.search.SearchState
import javax.inject.Inject

class SearchStateToParcelableMapper @Inject constructor() :
    Mapper<SearchState, SearchStateParcelable> {

    override fun map(from: SearchState): SearchStateParcelable {
        return SearchStateParcelable(
            loading = from.loading,
            data = from.data,
            suggestions = from.suggestions
        )
    }
}
