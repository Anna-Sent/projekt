package sample.kotlin.project.presentation.fragments.search

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchStateParcelable(
    val loading: Boolean = false,
    val data: String? = null,
    val suggestions: List<String> = emptyList()
) : Parcelable
