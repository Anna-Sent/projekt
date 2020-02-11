package sample.kotlin.project.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_search.*
import sample.kotlin.project.R

class SearchFragment : Fragment(), MviView<Action, SearchState> {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

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
    // save restore state

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonSearch.clicks()
            .map { UserAction.SearchAction(editTextQuery.text.toString()) }
            .subscribe(userActionsConsumer::accept)
        viewModel.bind(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.unbind()
    }

    private val userActionsConsumer = BehaviorRelay.create<Action>()
    override val userActions: Observable<Action> = userActionsConsumer.hide()

    override fun render(state: SearchState) {
        buttonSearch.isEnabled = !state.loading
        progressBar.visibility = if (state.loading) VISIBLE else GONE
        showData(state.data)
        if (state.throwable != null) {
            toast("Search failed")
        }
    }

    private fun showData(data: String?) {
        textView.text = data
    }

    private fun toast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}
