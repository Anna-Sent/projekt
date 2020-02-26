package sample.kotlin.project.presentation.fragments.search.adapters

import sample.kotlin.project.domain.pojo.search.RepositoryItem
import sample.kotlin.project.presentation.core.adapters.BaseDelegationAdapter
import sample.kotlin.project.presentation.core.adapters.BaseItemCallback

class RepositoryAdapter(private val onClickListener: (RepositoryItem) -> Unit) :
    BaseDelegationAdapter<RepositoryItem>(object : BaseItemCallback<RepositoryItem>() {
        override fun areItemsTheSame(
            oldItem: RepositoryItem,
            newItem: RepositoryItem
        ) = oldItem.index == newItem.index
    }) {

    init {
        delegatesManager += RepositoryDelegate { onClickListener.invoke(item(it)) }
        delegatesManager += ProgressDelegate()
    }
}
