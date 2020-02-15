package sample.kotlin.project.presentation.core

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import com.squareup.leakcanary.RefWatcher
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import io.logging.LogSystem
import io.logging.LogSystem.sens
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import org.slf4j.LoggerFactory
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import sample.kotlin.project.domain.core.mvi.Action
import sample.kotlin.project.domain.core.mvi.Event
import sample.kotlin.project.domain.core.mvi.MviView
import sample.kotlin.project.domain.core.mvi.State
import javax.inject.Inject

abstract class BaseActivity<S : State, A : Action, E : Event, Parcel : Parcelable, VM : BaseViewModel<S, A, E>> :
    AppCompatActivity(), HasAndroidInjector, MviView<A, S, E> {

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
    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private lateinit var viewModel: VM
    protected val disposables = CompositeDisposable()

    final override fun androidInjector() = androidInjector

    internal abstract val navigator: Navigator

    @LayoutRes
    protected abstract fun layoutId(): Int

    protected abstract fun getViewModel(provider: ViewModelProvider): VM

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(layoutId())
        stateSaver.restoreState(savedInstanceState)
        viewModel = getViewModel(ViewModelProvider(this, viewModelProviderFactory))
        disposables += viewModel.bind(this)
        disposables += events.subscribe({ handleEvent(it) }, ::unexpectedError)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        logger.debug("onNewIntent: {}", sens(intent))
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

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        logger.debug("onRestoreInstanceState: {}", sens(savedInstanceState))
    }

    override fun onResume() {
        super.onResume()
        logger.debug("onResume")
    }

    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)
        logger.debug("onAttachFragment: {}", fragment)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        logger.debug("onResumeFragments")
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        logger.debug("onPause")
        navigatorHolder.removeNavigator()
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

    override fun onDestroy() {
        super.onDestroy()
        logger.debug("onDestroy")
        disposables.dispose()
        refWatcher.watch(this)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, 0)
    }

    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
        overridePendingTransition(0, 0)
    }

    override fun startActivityForResult(intent: Intent?, requestCode: Int) {
        super.startActivityForResult(intent, requestCode)
        overridePendingTransition(0, 0)
    }

    protected val actionsRelay = BehaviorRelay.create<A>().toSerialized()
    final override val actions: Observable<A> = actionsRelay.hide()
    private val eventsRelay = PublishRelay.create<E>().toSerialized()
    final override val events = eventsRelay

    @CallSuper
    override fun render(state: S) {
        stateSaver.setState(state)
    }

    protected open fun handleEvent(event: E) {
        // override in nested classes if needed
    }

    protected fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
