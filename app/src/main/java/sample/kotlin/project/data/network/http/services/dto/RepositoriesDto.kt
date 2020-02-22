package sample.kotlin.project.data.network.http.services.dto

import com.google.gson.annotations.SerializedName

data class RepositoriesDto(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    @SerializedName("items")
    val items: List<RepositoryDto>,
    @SerializedName("total_count")
    val totalCount: Int
)
