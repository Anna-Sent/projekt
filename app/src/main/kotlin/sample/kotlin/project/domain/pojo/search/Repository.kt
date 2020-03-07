package sample.kotlin.project.domain.pojo.search

import sample.kotlin.project.domain.core.lists.Item

data class Repository(
    val pageIndexToDebug: Int,
    val pageNumberToDebug: Int,
    val id: Int,
    val fullName: String,
    val owner: Owner
) : Item<Repository>

object RepositoryProgressItem : Item<Repository>

data class RepositoryErrorItem(val error: Throwable) : Item<Repository>

typealias RepositoryItem = IndexedValue<Item<Repository>>
