package sample.kotlin.project.presentation.fragments.search

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.rxkotlin.plusAssign
import kotlinx.android.synthetic.main.fragment_search_content.*
import sample.kotlin.project.R
import sample.kotlin.project.domain.stores.search.pojo.*
import sample.kotlin.project.presentation.core.views.BaseFragment
import sample.kotlin.project.presentation.core.views.utils.toast
import sample.kotlin.project.presentation.fragments.search.adapters.RepositoryAdapter
import java.util.concurrent.TimeUnit

class SearchFragment : BaseFragment<SearchState, SearchAction, SearchEvent, SearchNavigationCommand,
        SearchStateParcelable, SearchViewModel>() {

    companion object {
        fun newInstance() = SearchFragment()
    }


    private var scrolledByUser = false

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val diff = totalItemCount - visibleItemCount - firstVisibleItemPosition
            if (diff <= 5 && firstVisibleItemPosition >= 0) {
                viewModel.dispatch(SearchAction.OnScrolledToBottom)
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            if (!scrolledByUser) {
                scrolledByUser = true
            }
        }
    }

    private val adapter =
        RepositoryAdapter { toast("${it.value.fullName} ${it.value.owner.login}") }

    override val layoutId = R.layout.fragment_search

    override fun provideViewModel(provider: ViewModelProvider) =
        provider[SearchViewModel::class.java]

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.dispatch(SearchAction.LoadSuggestions)
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
        recyclerView.removeOnScrollListener(scrollListener)
        super.onDestroyView()
    }

    override fun render(state: SearchState) {
        textViewConnected.visibility = if (state.connected) VISIBLE else GONE
        buttonSearch.isEnabled = state.loadingStatus == null
        progressBar.visibility =
            if (state.loadingStatus == LoadingStatus.FIRST_PAGE_INITIAL) VISIBLE else GONE
        swipeRefreshLayout.isRefreshing = state.loadingStatus == LoadingStatus.FIRST_PAGE_REFRESH
        adapter.items = state.repositories
        if (!scrolledByUser) {
            // TODO: проверить, нужно или нет
            recyclerView.scrollToPosition(0)
        }
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
        recyclerView.addOnScrollListener(scrollListener)
        swipeRefreshLayout.setOnRefreshListener { viewModel.dispatch(SearchAction.OnRefresh) }
    }
}
