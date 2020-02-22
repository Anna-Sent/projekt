package sample.kotlin.project.presentation.fragments.search.adapters

import android.view.View
import kotlinx.android.synthetic.main.item_repository.view.*
import sample.kotlin.project.domain.pojo.search.Repository
import sample.kotlin.project.presentation.core.adapters.BaseViewHolder

internal class RepositoryViewHolder(itemView: View) : BaseViewHolder<Repository>(itemView) {

    override fun bind(item: Repository) {
        itemView.textViewFullName.text = item.fullName
        itemView.textViewOwnerLogin.text = item.owner.login
    }
}
