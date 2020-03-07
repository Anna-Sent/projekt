package sample.kotlin.project.presentation.fragments.search.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_repository_error.view.buttonRetryNextPage
import sample.kotlin.project.R
import sample.kotlin.project.domain.pojo.search.RepositoryErrorItem
import sample.kotlin.project.domain.pojo.search.RepositoryItem
import sample.kotlin.project.presentation.core.adapters.BaseAdapterDelegate

internal class ErrorDelegate(
    private val onClickListener: (Int) -> Unit
) : BaseAdapterDelegate<RepositoryItem, RepositoryItem, ErrorViewHolder>() {

    override fun isForViewType(item: RepositoryItem) = item.value is RepositoryErrorItem

    override fun onCreateViewHolder(parent: ViewGroup): ErrorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_repository_error, parent, false)
        val viewHolder = ErrorViewHolder(view)
        viewHolder.setOnAdapterPositionClickListener(view.buttonRetryNextPage, onClickListener)
        return viewHolder
    }
}
