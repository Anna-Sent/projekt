package sample.kotlin.project.presentation.fragments.search

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.rxkotlin.plusAssign
import kotlinx.android.synthetic.main.fragment_search_content.*
import sample.kotlin.project.R
import sample.kotlin.project.domain.stores.search.pojo.SearchAction
import sample.kotlin.project.domain.stores.search.pojo.SearchEvent
import sample.kotlin.project.domain.stores.search.pojo.SearchNavigationCommand
import sample.kotlin.project.domain.stores.search.pojo.SearchState
import sample.kotlin.project.presentation.core.views.BaseFragment
import sample.kotlin.project.presentation.core.views.utils.toast
import sample.kotlin.project.presentation.fragments.search.adapters.RepositoryAdapter
import sample.kotlin.project.presentation.fragments.search.state.SearchStateParcelable
import java.util.concurrent.TimeUnit

class SearchFragment : BaseFragment<SearchState, SearchAction, SearchEvent, SearchNavigationCommand,
        SearchStateParcelable, SearchViewModel>() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private val adapter = RepositoryAdapter { toast("${it.fullName} ${it.owner.login}") }

    override val layoutId = R.layout.fragment_search

    override fun provideViewModel(provider: ViewModelProvider) =
        provider[SearchViewModel::class.java]

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.dispatch(SearchAction.OnActivityCreatedFirstTime)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        disposables += buttonSearch.clicks()
            .map { SearchAction.OnSearchClick(editTextQuery.text.toString().trim()) }
            .subscribe(viewModel::dispatch, ::unexpectedError)
        disposables += editTextQuery.textChanges()
            .debounce(250, TimeUnit.MILLISECONDS)
            .map { SearchAction.OnSearchQueryChanged(it.toString().trim()) }
            .subscribe(viewModel::dispatch, ::unexpectedError)
    }

    override fun onDestroyView() {
        recyclerView.adapter = null
        super.onDestroyView()
    }

    override fun render(state: SearchState) {
        textViewConnected.visibility = if (state.connected) VISIBLE else GONE
        buttonSearch.isEnabled = !state.loading
        progressBar.visibility = if (state.loading) VISIBLE else GONE
        adapter.items = state.repositories
        val autoCompleteAdapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line, state.suggestions
        )
        editTextQuery.setAdapter(autoCompleteAdapter)
    }

    override fun handle(event: SearchEvent) {
        when (event) {

            is SearchEvent.SearchFailureEvent -> toast("Search failed\n${event.error}")
        }
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }
}
