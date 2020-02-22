package sample.kotlin.project.presentation.core.views

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import io.logging.LogSystem
import io.logging.LogSystem.sens
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import sample.kotlin.project.domain.core.mvi.MviView
import sample.kotlin.project.domain.core.mvi.pojo.Action
import sample.kotlin.project.domain.core.mvi.pojo.Event
import sample.kotlin.project.domain.core.mvi.pojo.NavigationCommand
import sample.kotlin.project.domain.core.mvi.pojo.State
import javax.inject.Inject

abstract class BaseActivity<S : State, A : Action, E : Event, NC : NavigationCommand,
        Parcel : Parcelable, VM : BaseViewModel<S, A, E, NC>> :
    AppCompatActivity(), HasAndroidInjector, MviView<S, E> {

    final override fun toString() = super.toString()
    protected val logger: Logger = LoggerFactory.getLogger(toString())
    protected fun unexpectedError(throwable: Throwable) {
        logger.error("Unexpected error occurred", throwable)
        LogSystem.report(logger, "Unexpected error occurred", throwable)
    }

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>
    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory
    @Inject
    lateinit var stateSaver: StateSaver<S, Parcel>
    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    protected lateinit var viewModel: VM
    protected val disposables = CompositeDisposable()
    private val statesDisposables = CompositeDisposable()

    final override fun androidInjector() = androidInjector

    internal abstract val navigator: Navigator

    @LayoutRes
    protected abstract fun layoutId(): Int

    protected abstract fun provideViewModel(provider: ViewModelProvider): VM

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(layoutId())
        stateSaver.restoreState(savedInstanceState)
        viewModel = provideViewModel(ViewModelProvider(this, viewModelProviderFactory))
        logger.debug("provided view model: {}", viewModel)
        statesDisposables += viewModel.statesObservable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::handleState, ::unexpectedError)
    }

    private fun handleState(state: S) {
        stateSaver.state = state
        render(state)
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
        viewModel.eventsHolder.attachView(this)
    }

    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)
        logger.debug("onAttachFragment: {}", fragment)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        logger.debug("onResumeFragments")
        logger.debug("attached navigation holder: {}", navigatorHolder)
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        logger.debug("onPause")
        logger.debug("detached navigation holder: {}", navigatorHolder)
        navigatorHolder.removeNavigator()
        viewModel.eventsHolder.detachView()
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
        statesDisposables.dispose()
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

    override fun render(state: S) {
        // override in nested classes if needed
    }

    override fun handleEvent(event: E) {
        // override in nested classes if needed
    }
}
