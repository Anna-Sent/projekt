package sample.kotlin.project.presentation.fragments.search.adapters

import android.view.View
import kotlinx.android.synthetic.main.item_repository.*
import sample.kotlin.project.domain.pojo.search.Repository
import sample.kotlin.project.domain.pojo.search.RepositoryItem
import sample.kotlin.project.presentation.core.adapters.BaseViewHolder

internal class RepositoryViewHolder(containerView: View) :
    BaseViewHolder<RepositoryItem>(containerView) {

    override fun bind(item: RepositoryItem) {
        val repository = item.value as Repository
        textViewIndex.text = repository.pageIndexToDebug.toString()
        val resId = when (repository.pageIndexToDebug) {
            0 -> android.R.color.holo_red_dark
            29 -> android.R.color.holo_green_dark
            else -> 0
        }
        textViewIndex.setBackgroundResource(resId)
        textViewFullName.text = repository.fullName
        textViewOwnerLogin.text = repository.owner.login
    }
}
