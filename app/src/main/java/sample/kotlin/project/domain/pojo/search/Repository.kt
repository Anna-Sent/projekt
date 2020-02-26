package sample.kotlin.project.domain.pojo.search

import sample.kotlin.project.domain.core.lists.Item

data class Repository(
    val id: Int,
    val fullName: String,
    val owner: Owner
) : Item<Repository>

object RepositoryProgress : Item<Repository>

typealias RepositoryItem = IndexedValue<Item<Repository>>
