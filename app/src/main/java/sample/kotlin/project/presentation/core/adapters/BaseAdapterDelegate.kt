package sample.kotlin.project.presentation.core.adapters

import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

abstract class BaseAdapterDelegate<I : T, T, VH : BaseViewHolder<I>> :
    AbsListItemAdapterDelegate<I, T, VH>() {

    final override fun isForViewType(item: T, items: List<T>, position: Int) = isForViewType(item)

    final override fun onBindViewHolder(item: I, viewHolder: VH, payloads: List<Any>) =
        if (payloads.isEmpty()) {
            viewHolder.bind(item)
        } else {
            viewHolder.bind(item, payloads)
        }

    protected abstract fun isForViewType(item: T): Boolean
}
