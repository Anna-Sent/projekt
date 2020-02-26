package sample.kotlin.project.presentation.fragments.search.adapters

import android.view.View
import kotlinx.android.synthetic.main.item_repository.view.*
import sample.kotlin.project.domain.pojo.search.Repository
import sample.kotlin.project.domain.pojo.search.RepositoryItem
import sample.kotlin.project.presentation.core.adapters.BaseViewHolder

internal class RepositoryViewHolder(itemView: View) :
    BaseViewHolder<RepositoryItem>(itemView) {

    override fun bind(item: RepositoryItem) {
        itemView.textViewFullName.text = (item.value as Repository).fullName
        itemView.textViewOwnerLogin.text = (item.value as Repository).owner.login
    }
}
