package sample.kotlin.project.presentation.fragments.search

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import sample.kotlin.project.domain.core.mappers.Mapper
import sample.kotlin.project.domain.stores.search.pojo.SearchState
import javax.inject.Inject

@Parcelize
data class SearchStateParcelable(
    val lastQuery: String? = null,
    val suggestions: List<String> = emptyList()
) : Parcelable

class SearchStateToParcelableMapper
@Inject constructor(
) : Mapper<SearchState, SearchStateParcelable> {

    override fun map(from: SearchState) =
        SearchStateParcelable(
            lastQuery = from.lastQuery,
            suggestions = from.suggestions
        )
}

class SearchStateFromParcelableMapper
@Inject constructor(
) : Mapper<SearchStateParcelable, SearchState> {

    override fun map(from: SearchStateParcelable) =
        SearchState(
            lastQuery = from.lastQuery,
            suggestions = from.suggestions
        )
}
