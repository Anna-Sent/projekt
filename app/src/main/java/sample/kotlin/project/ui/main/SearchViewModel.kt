package sample.kotlin.project.ui.main

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.Disposable

class SearchViewModel : ViewModel() {

    private val store = SearchStore()

    private val wiring = store.wire()
    private var viewBinding: Disposable? = null

    override fun onCleared() {
        wiring.dispose()
    }

    fun bind(view: MviView<Action, SearchState>) {
        viewBinding = store.bind(view)
    }

    fun unbind() {
        viewBinding?.dispose()
    }
}
