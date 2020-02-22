package sample.kotlin.project.presentation.fragments.search.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import sample.kotlin.project.R
import sample.kotlin.project.domain.pojo.search.Repository
import sample.kotlin.project.presentation.core.adapters.BaseAdapterDelegateUniform

internal class RepositoryDelegate(
    private val onClickListener: (Int) -> Unit
) : BaseAdapterDelegateUniform<Repository, RepositoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup): RepositoryViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_repository, parent, false)
        val viewHolder = RepositoryViewHolder(view)
        viewHolder.setOnAdapterPositionClickListener(view, onClickListener)
        return viewHolder
    }
}
