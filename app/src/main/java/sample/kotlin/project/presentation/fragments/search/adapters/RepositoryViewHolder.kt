package sample.kotlin.project.presentation.fragments.search.adapters

import android.view.View
import kotlinx.android.synthetic.main.item_repository.view.*
import sample.kotlin.project.domain.pojo.search.IndexedRepository
import sample.kotlin.project.presentation.core.adapters.BaseViewHolder

internal class RepositoryViewHolder(itemView: View) :
    BaseViewHolder<IndexedRepository>(itemView) {

    override fun bind(item: IndexedRepository) {
        itemView.textViewFullName.text = item.value.fullName
        itemView.textViewOwnerLogin.text = item.value.owner.login
    }
}
