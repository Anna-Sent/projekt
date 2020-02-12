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
import sample.kotlin.project.domain.search.SearchAction
import sample.kotlin.project.domain.search.SearchEvent
import sample.kotlin.project.domain.search.SearchState
import sample.kotlin.project.presentation.core.BaseFragment
import java.util.concurrent.TimeUnit

// live date? coroutines?
// correct handling of subscribe?
// use https://api.github.com/search/repositories?q=tetris+language:assembly&sort=stars&order=desc
// data/domain/presentation
// DI
// see bb, rr
// auto complete
// use user action where possible?
// inject initial state from arguments
// TODO: test MVI
// single event?
// lost state/response
// save restore state
// exclude something from parcel
class SearchFragment :
    BaseFragment<SearchState, SearchAction, SearchEvent, SearchStateParcelable, SearchViewModel>() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    override fun layoutId() = R.layout.fragment_search

    override fun getViewModel(provider: ViewModelProvider) =
        provider[SearchViewModel::class.java]

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO Check
        userActions.accept(SearchAction.LoadSuggestionsAction)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        disposables += buttonSearch.clicks()
            .map { SearchAction.SearchClickAction(editTextQuery.text.toString().trim()) }
            .subscribe(userActions::accept)
        disposables += editTextQuery.textChanges()
            .debounce(250, TimeUnit.MILLISECONDS)
            .map { SearchAction.SearchQueryChangeAction(it.toString().trim()) }
            .subscribe(userActions::accept)
    }

    override fun render(state: SearchState) {
        super.render(state)
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
