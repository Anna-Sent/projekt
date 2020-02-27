package sample.kotlin.project.presentation.fragments.search.adapters

import android.view.View
import kotlinx.android.synthetic.main.item_repository.*
import sample.kotlin.project.domain.pojo.search.Repository
import sample.kotlin.project.domain.pojo.search.RepositoryItem
import sample.kotlin.project.presentation.core.adapters.BaseViewHolder

internal class RepositoryViewHolder(containerView: View) :
    BaseViewHolder<RepositoryItem>(containerView) {

    override fun bind(item: RepositoryItem) {
        textViewFullName.text = (item.value as Repository).fullName
        textViewOwnerLogin.text = (item.value as Repository).owner.login
    }
}
