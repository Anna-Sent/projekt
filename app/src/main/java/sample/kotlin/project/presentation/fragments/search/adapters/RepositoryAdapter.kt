package sample.kotlin.project.presentation.fragments.search.adapters

import sample.kotlin.project.domain.pojo.search.IndexedRepository
import sample.kotlin.project.presentation.core.adapters.BaseDelegationAdapter
import sample.kotlin.project.presentation.core.adapters.BaseItemCallback

class RepositoryAdapter(private val onClickListener: (IndexedRepository) -> Unit) :
    BaseDelegationAdapter<IndexedRepository>(object : BaseItemCallback<IndexedRepository>() {
        override fun areItemsTheSame(
            oldItem: IndexedRepository,
            newItem: IndexedRepository
        ) = oldItem.index == newItem.index
    }) {

    init {
        delegatesManager += RepositoryDelegate { onClickListener.invoke(item(it)) }
    }
}
