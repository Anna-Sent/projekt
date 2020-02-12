package sample.kotlin.project.presentation.core

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jakewharton.rxrelay2.BehaviorRelay
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import sample.kotlin.project.domain.mvi.Action
import sample.kotlin.project.domain.mvi.MviView
import sample.kotlin.project.domain.mvi.State
import javax.inject.Inject

abstract class BaseFragment<S : State, A : Action, Parcel : Parcelable, VM : BaseViewModel<S, A>> :
    Fragment(), HasAndroidInjector, MviView<A, S> {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>
    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory
    @Inject
    lateinit var stateSaver: StateSaver<S, Parcel>

    private lateinit var viewModel: VM
    protected val disposables = CompositeDisposable()

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        stateSaver.restoreState(savedInstanceState)
        viewModel = getViewModel(ViewModelProvider(this, viewModelProviderFactory))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.bind(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.unbind()
        disposables.clear()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        stateSaver.saveInstanceState(outState)
    }

    final override fun androidInjector() = androidInjector

    @LayoutRes
    protected abstract fun layoutId(): Int

    protected abstract fun getViewModel(provider: ViewModelProvider): VM

    // TODO protected abstract fun onEvent(event: E)

    protected val userActions = BehaviorRelay.create<A>().toSerialized()
    final override val actions: Observable<A> = userActions.hide()

    @CallSuper
    override fun render(state: S) {
        stateSaver.setState(state)
    }

    protected fun toast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}
