package sample.kotlin.project.presentation.core.adapters

abstract class BaseAdapterDelegateUniform<I, VH : BaseViewHolder<I>> :
    BaseAdapterDelegate<I, I, VH>() {

    final override fun isForViewType(item: I) = true
}
