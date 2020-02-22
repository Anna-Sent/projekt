package sample.kotlin.project.presentation.core.adapters

import androidx.recyclerview.widget.DiffUtil

abstract class BaseItemCallback<I> : DiffUtil.ItemCallback<I>() {

    override fun areContentsTheSame(oldItem: I, newItem: I) = sameContents(oldItem, newItem)

    private fun sameContents(oldItem: I, newItem: I) = oldItem == newItem
}
