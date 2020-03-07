package sample.kotlin.project.data.network.http.dto.search

import com.google.gson.annotations.SerializedName

data class RepositoryDto(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("default_branch")
    val defaultBranch: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("fork")
    val fork: Boolean,
    @SerializedName("forks_count")
    val forksCount: Int,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("homepage")
    val homepage: String,
    @SerializedName("html_url")
    val htmlUrl: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("language")
    val language: String,
    @SerializedName("master_branch")
    val masterBranch: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("node_id")
    val nodeId: String,
    @SerializedName("open_issues_count")
    val openIssuesCount: Int,
    @SerializedName("owner")
    val owner: OwnerDto,
    @SerializedName("private")
    val isPrivate: Boolean,
    @SerializedName("pushed_at")
    val pushedAt: String,
    @SerializedName("score")
    val score: Double,
    @SerializedName("size")
    val size: Int,
    @SerializedName("stargazers_count")
    val stargazersCount: Int,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("watchers_count")
    val watchersCount: Int
)
