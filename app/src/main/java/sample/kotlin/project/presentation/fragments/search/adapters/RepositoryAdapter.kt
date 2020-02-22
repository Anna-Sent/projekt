package sample.kotlin.project.presentation.fragments.search.adapters

import sample.kotlin.project.domain.pojo.search.Repository
import sample.kotlin.project.presentation.core.adapters.BaseDelegationAdapter
import sample.kotlin.project.presentation.core.adapters.BaseItemCallback

class RepositoryAdapter(private val onClickListener: (Repository) -> Unit) :
    BaseDelegationAdapter<Repository>(object : BaseItemCallback<Repository>() {
        override fun areItemsTheSame(oldItem: Repository, newItem: Repository) =
            oldItem.id == newItem.id
    }) {

    init {
        delegatesManager += RepositoryDelegate { onClickListener.invoke(item(it)) }
    }
}
