package sample.kotlin.project.data.network.http.dto.search

import com.google.gson.annotations.SerializedName

data class OwnerDto(
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("gravatar_id")
    val gravatarId: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("login")
    val login: String,
    @SerializedName("node_id")
    val nodeId: String,
    @SerializedName("received_events_url")
    val receivedEventsUrl: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("url")
    val url: String
)
