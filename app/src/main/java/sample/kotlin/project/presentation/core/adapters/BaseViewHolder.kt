package sample.kotlin.project.presentation.core.adapters

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<I> protected constructor(itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    protected val context: Context get() = itemView.context

    internal abstract fun bind(item: I)

    internal open fun bind(item: I, payloads: List<Any>) = bind(item)

    internal open fun onDetached() {
        // override in nested classes if needed
    }

    internal fun setOnAdapterPositionClickListener(
        view: View,
        onClickListener: (Int) -> Unit
    ) = view.setOnClickListener {
        val adapterPosition = adapterPosition
        if (adapterPosition != RecyclerView.NO_POSITION) {
            onClickListener.invoke(adapterPosition)
        }
    }
}
