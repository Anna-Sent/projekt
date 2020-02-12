package sample.kotlin.project.presentation.fragments.search

import sample.kotlin.project.domain.Mapper
import sample.kotlin.project.domain.search.SearchState
import javax.inject.Inject

class SearchStateFromParcelableMapper @Inject constructor() :
    Mapper<SearchStateParcelable, SearchState> {

    override fun map(from: SearchStateParcelable): SearchState {
        return SearchState(
            loading = from.loading,
            data = from.data,
            suggestions = from.suggestions
        )
    }
}
