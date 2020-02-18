package sample.kotlin.project.presentation.fragments.search

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.rxkotlin.plusAssign
import kotlinx.android.synthetic.main.fragment_search.*
import sample.kotlin.project.R
import sample.kotlin.project.domain.stores.search.data.SearchAction
import sample.kotlin.project.domain.stores.search.data.SearchEvent
import sample.kotlin.project.domain.stores.search.data.SearchState
import sample.kotlin.project.presentation.core.BaseFragment
import sample.kotlin.project.presentation.fragments.search.state.SearchStateParcelable
import java.util.concurrent.TimeUnit

class SearchFragment :
    BaseFragment<SearchState, SearchAction, SearchEvent, SearchStateParcelable, SearchViewModel>() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    override fun layoutId() = R.layout.fragment_search

    override fun provideViewModel(provider: ViewModelProvider) = provider[SearchViewModel::class.java]

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.postAction(SearchAction.LoadSuggestionsAction)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        disposables += buttonSearch.clicks()
            .map { SearchAction.SearchClickAction(editTextQuery.text.toString().trim()) }
            .subscribe(viewModel::postAction, ::unexpectedError)
        disposables += editTextQuery.textChanges()
            .debounce(250, TimeUnit.MILLISECONDS)
            .map { SearchAction.SearchQueryChangeAction(it.toString().trim()) }
            .subscribe(viewModel::postAction, ::unexpectedError)
    }

    override fun render(state: SearchState) {
        buttonSearch.isEnabled = !state.loading
        progressBar.visibility = if (state.loading) VISIBLE else GONE
        textView.visibility = if (state.data.isNullOrEmpty()) GONE else VISIBLE
        textView.text = state.data
        val adapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line, state.suggestions
        )
        editTextQuery.setAdapter(adapter)
    }

    override fun handleEvent(event: SearchEvent) {
        when (event) {

            is SearchEvent.SearchFailureEvent -> toast("Search failed")
        }
    }
}
