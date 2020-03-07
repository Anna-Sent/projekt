package sample.kotlin.project.presentation.fragments.search.adapters

import android.view.View
import kotlinx.android.synthetic.main.item_repository_error.textViewError
import sample.kotlin.project.domain.pojo.search.RepositoryErrorItem
import sample.kotlin.project.domain.pojo.search.RepositoryItem
import sample.kotlin.project.presentation.core.adapters.BaseViewHolder

internal class ErrorViewHolder(containerView: View) :
    BaseViewHolder<RepositoryItem>(containerView) {

    override fun bind(item: RepositoryItem) {
        textViewError.text = (item.value as RepositoryErrorItem).error.toString()
    }
}
