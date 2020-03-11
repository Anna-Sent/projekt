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
import kotlinx.android.synthetic.main.fragment_search_content.buttonRetry
import kotlinx.android.synthetic.main.fragment_search_content.buttonSearch
import kotlinx.android.synthetic.main.fragment_search_content.editTextQuery
import kotlinx.android.synthetic.main.fragment_search_content.layoutError
import kotlinx.android.synthetic.main.fragment_search_content.layoutNothingFound
import kotlinx.android.synthetic.main.fragment_search_content.progressBar
import kotlinx.android.synthetic.main.fragment_search_content.recyclerView
import kotlinx.android.synthetic.main.fragment_search_content.swipeRefreshLayout
import kotlinx.android.synthetic.main.fragment_search_content.textViewConnected
import kotlinx.android.synthetic.main.fragment_search_content.textViewError
import sample.kotlin.project.R
import sample.kotlin.project.domain.pojo.search.Repository
import sample.kotlin.project.domain.stores.search.pojo.SearchAction
import sample.kotlin.project.domain.stores.search.pojo.SearchAction.OnLoadSuggestions
import sample.kotlin.project.domain.stores.search.pojo.SearchAction.OnRefresh
import sample.kotlin.project.domain.stores.search.pojo.SearchAction.OnRetryClick
import sample.kotlin.project.domain.stores.search.pojo.SearchAction.OnRetryNextPageClick
import sample.kotlin.project.domain.stores.search.pojo.SearchAction.OnScrolledToBottom
import sample.kotlin.project.domain.stores.search.pojo.SearchAction.OnSearchClick
import sample.kotlin.project.domain.stores.search.pojo.SearchAction.OnSearchQueryChanged
import sample.kotlin.project.domain.stores.search.pojo.SearchEvent
import sample.kotlin.project.domain.stores.search.pojo.SearchEvent.SearchRefreshFailureEvent
import sample.kotlin.project.domain.stores.search.pojo.SearchNavigationCommand
import sample.kotlin.project.domain.stores.search.pojo.SearchRequestType.FIRST_PAGE
import sample.kotlin.project.domain.stores.search.pojo.SearchRequestType.FIRST_PAGE_REFRESH
import sample.kotlin.project.domain.stores.search.pojo.SearchRequestType.FIRST_PAGE_RETRY
import sample.kotlin.project.domain.stores.search.pojo.SearchState
import sample.kotlin.project.presentation.core.views.BaseFragment
import sample.kotlin.project.presentation.core.views.extensions.hideKeyboard
import sample.kotlin.project.presentation.core.views.extensions.toast
import sample.kotlin.project.presentation.core.views.extensions.unexpectedError
import sample.kotlin.project.presentation.fragments.search.adapters.RepositoryAdapter
import java.util.concurrent.TimeUnit

class SearchFragment : BaseFragment<SearchState, SearchAction, SearchEvent, SearchNavigationCommand,
    SearchStateParcelable, SearchViewModel>() {

    companion object {
        private const val DEBOUNCE_MILLISECONDS = 250L
        private const val ITEMS_COUNT = 5
        fun newInstance() = SearchFragment()
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val diff = totalItemCount - visibleItemCount - firstVisibleItemPosition
            if (diff <= ITEMS_COUNT && firstVisibleItemPosition >= 0) {
                viewModel.dispatch(OnScrolledToBottom)
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            // no op
        }
    }

    private val adapter = RepositoryAdapter(::onRepositoryClick, ::onRetryNextPage)

    private fun onRepositoryClick(repository: Repository) =
        repository.run { toast("$fullName ${owner.login}") }

    private fun onRetryNextPage() = viewModel.dispatch(OnRetryNextPageClick)

    override val layoutId = R.layout.fragment_search

    override fun provideViewModel(provider: ViewModelProvider) =
        provider[SearchViewModel::class.java]

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.dispatch(OnLoadSuggestions)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        disposables += buttonSearch.clicks()
            .map { OnSearchClick(editTextQuery.text.toString().trim()) }
            .doOnNext { hideKeyboard() }
            .subscribe(viewModel::dispatch, ::unexpectedError)
        disposables += editTextQuery.textChanges()
            .debounce(DEBOUNCE_MILLISECONDS, TimeUnit.MILLISECONDS)
            .map { OnSearchQueryChanged(it.toString().trim()) }
            .subscribe(viewModel::dispatch, ::unexpectedError)
        buttonRetry.setOnClickListener { viewModel.dispatch(OnRetryClick) }
    }

    override fun onDestroyView() {
        recyclerView.adapter = null
        recyclerView.removeOnScrollListener(scrollListener)
        super.onDestroyView()
    }

    override fun render(state: SearchState) {
        // TODO Refactoring: state methods
        textViewConnected.visibility = if (state.connected) VISIBLE else GONE

        val notInProgress = state.requestType == null
        val somethingLoaded = notInProgress && state.lastQuery != null
        val isLoadingFirstPage = state.requestType == FIRST_PAGE ||
            state.requestType == FIRST_PAGE_RETRY
        val isRefreshing = state.requestType == FIRST_PAGE_REFRESH
        val failed = state.error != null

        buttonSearch.isEnabled = notInProgress
        progressBar.visibility = if (isLoadingFirstPage) VISIBLE else GONE
        swipeRefreshLayout.isEnabled = somethingLoaded || isRefreshing
        swipeRefreshLayout.isRefreshing = isRefreshing

        adapter.items = state.repositories

        layoutError.visibility = if (failed && somethingLoaded) VISIBLE else GONE
        textViewError.text = state.error?.toString()
        val nothingFound =
            !failed && somethingLoaded && state.lastQuery != null && state.repositories.isEmpty()
        layoutNothingFound.visibility = if (nothingFound) VISIBLE else GONE

        val autoCompleteAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line, state.suggestions
        )
        editTextQuery.setAdapter(autoCompleteAdapter)
    }

    override fun handle(event: SearchEvent) {
        when (event) {

            is SearchRefreshFailureEvent -> toast("Search failed\n${event.error}")
        }
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        recyclerView.addOnScrollListener(scrollListener)
        swipeRefreshLayout.setOnRefreshListener { viewModel.dispatch(OnRefresh) }
    }
}
