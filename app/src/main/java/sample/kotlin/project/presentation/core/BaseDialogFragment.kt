package sample.kotlin.project.presentation.core

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.leakcanary.RefWatcher
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import io.logging.LogSystem
import io.logging.LogSystem.sens
import io.reactivex.disposables.CompositeDisposable
import org.slf4j.LoggerFactory
import sample.kotlin.project.domain.core.mvi.Action
import sample.kotlin.project.domain.core.mvi.Event
import sample.kotlin.project.domain.core.mvi.MviView
import sample.kotlin.project.domain.core.mvi.State
import javax.inject.Inject

abstract class BaseDialogFragment<S : State, A : Action, E : Event, Parcel : Parcelable, VM : BaseViewModel<S, A, E>> :
    DialogFragment(), HasAndroidInjector, MviView<S, E> {

    final override fun toString() = super.toString()
    private val logger = LoggerFactory.getLogger(toString())
    protected fun unexpectedError(throwable: Throwable) {
        logger.error("Unexpected error occurred", throwable)
        LogSystem.report(logger, "Unexpected error occurred", throwable)
    }

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>
    @Inject
    lateinit var refWatcher: RefWatcher
    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory
    @Inject
    lateinit var stateSaver: StateSaver<S, Parcel>

    protected lateinit var viewModel: VM
    protected val disposables = CompositeDisposable() // for rx view bindings

    final override fun androidInjector() = androidInjector

    @LayoutRes
    protected open fun layoutId(): Int = 0

    @CallSuper
    protected open fun initUi(savedInstanceState: Bundle?) {
        // override in nested classes if needed
    }

    protected abstract fun createDialog(
        view: View?,
        savedInstanceState: Bundle?
    ): Dialog

    protected abstract fun buildViewModel(provider: ViewModelProvider): VM

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        logger.debug("onAttach: {}", context)
    }

    override fun onAttachFragment(childFragment: Fragment) {
        super.onAttachFragment(childFragment)
        logger.debug("onAttachFragment: {}", childFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logger.debug("onCreate: {}", sens(savedInstanceState))
        stateSaver.restoreState(savedInstanceState)
        viewModel = buildViewModel(ViewModelProvider(this, viewModelProviderFactory))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        logger.debug("onCreateView: {}", sens(savedInstanceState))
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logger.debug("onViewCreated: {}", sens(savedInstanceState))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        logger.debug("onActivityCreated: {}", sens(savedInstanceState))
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        logger.debug("onViewStateRestored: {}", sens(savedInstanceState))
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        logger.debug("onCreateDialog: {}", sens(savedInstanceState))
        var view: View? = null
        if (layoutId() != 0) {
            val inflater = LayoutInflater.from(requireContext())
            view = inflater.inflate(layoutId(), null)
            initUi(savedInstanceState)
            viewModel.statesLiveData.observe(viewLifecycleOwner,
                Observer {
                    stateSaver.setState(it)
                    render(it)
                })
            viewModel.eventsLiveData.observe(viewLifecycleOwner, Observer(::handleEvent))
        }
        return createDialog(view, savedInstanceState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        logger.debug(
            "onActivityResult: requestCode={}; resultCode={}; data={}",
            requestCode, resultCode, sens(data)
        )
    }

    override fun onStart() {
        super.onStart()
        logger.debug("onStart")
    }

    override fun onResume() {
        super.onResume()
        logger.debug("onResume")
    }

    override fun onPause() {
        super.onPause()
        logger.debug("onPause")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        stateSaver.saveInstanceState(outState)
        logger.debug("onSaveInstanceState: {}", sens(outState))
    }

    override fun onStop() {
        super.onStop()
        logger.debug("onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        logger.debug("onDestroyView")
        disposables.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        logger.debug("onDestroy")
        refWatcher.watch(this)
    }

    override fun onDetach() {
        super.onDetach()
        logger.debug("onDetach")
    }

    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
        requireActivity().overridePendingTransition(0, 0)
    }

    override fun startActivityForResult(intent: Intent?, requestCode: Int) {
        super.startActivityForResult(intent, requestCode)
        requireActivity().overridePendingTransition(0, 0)
    }

    protected fun toast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}