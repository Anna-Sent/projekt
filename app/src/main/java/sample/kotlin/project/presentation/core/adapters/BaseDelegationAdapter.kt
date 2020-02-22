package sample.kotlin.project.presentation.core.adapters

import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

abstract class BaseDelegationAdapter<I>
protected constructor(
    itemCallback: BaseItemCallback<I>
) : AsyncListDifferDelegationAdapter<I>(itemCallback) {

    protected fun item(position: Int): I = items[position]

    protected operator fun <T> AdapterDelegatesManager<T>.plusAssign(
        repositoryDelegate: AdapterDelegate<T>
    ) {
        addDelegate(repositoryDelegate)
    }
}
