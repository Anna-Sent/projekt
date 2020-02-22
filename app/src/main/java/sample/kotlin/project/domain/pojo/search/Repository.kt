package sample.kotlin.project.domain.pojo.search

data class Repository(
    val id: Int,
    val fullName: String,
    val owner: Owner
)
