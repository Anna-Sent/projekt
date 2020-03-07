package sample.kotlin.project.presentation.core.views

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjection
import dagger.android.HasAndroidInjector
import io.logging.LogSystem.sens
import io.reactivex.android.schedulers.AndroidSchedulers
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
import sample.kotlin.project.presentation.core.views.extensions.unexpectedError
import javax.inject.Inject

@Suppress("TooManyFunctions")
abstract class BaseActivity<S : State, A : Action, E : Event, NC : NavigationCommand,
    Parcel : Parcelable, VM : BaseViewModel<S, A, E, NC>> :
    AppCompatActivity(), HasAndroidInjector, MviView<S, E> {

    final override fun toString() = super.toString()
    val logger: Logger = LoggerFactory.getLogger(toString())

    @Inject
    internal lateinit var baseView: BaseView<S, A, E, NC, Parcel, VM>
    protected val viewModel get() = baseView.viewModel
    protected val disposables get() = baseView.disposables

    @Inject
    internal lateinit var navigatorHolder: NavigatorHolder

    final override fun androidInjector() = baseView.androidInjector

    internal abstract val navigator: Navigator

    @get:LayoutRes
    protected abstract val layoutId: Int

    protected abstract fun provideViewModel(provider: ViewModelProvider): VM

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        baseView.stateSaver.restoreState(savedInstanceState)
        baseView.viewModel =
            provideViewModel(ViewModelProvider(this, baseView.viewModelProviderFactory))
        logger.debug("provided view model: ${baseView.viewModel}")
        baseView.statesDisposables += baseView.viewModel.statesObservable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::handle, ::unexpectedError)
    }

    private fun handle(state: S) {
        baseView.stateSaver.state = state
        render(state)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        logger.debug("onNewIntent: ${sens(intent)}")
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
        logger.debug("onRestoreInstanceState: ${sens(savedInstanceState)}")
    }

    override fun onResume() {
        super.onResume()
        logger.debug("onResume")
        baseView.viewModel.eventsHolder.attachView(this)
    }

    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)
        logger.debug("onAttachFragment: $fragment")
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        logger.debug("onResumeFragments")
        logger.debug("attached navigation holder: $navigatorHolder")
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        logger.debug("onPause")
        logger.debug("detached navigation holder: $navigatorHolder")
        navigatorHolder.removeNavigator()
        baseView.viewModel.eventsHolder.detachView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        baseView.stateSaver.saveInstanceState(outState)
        logger.debug("onSaveInstanceState: ${sens(outState)}")
    }

    override fun onStop() {
        super.onStop()
        logger.debug("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        logger.debug("onDestroy")
        baseView.disposables.dispose()
        baseView.statesDisposables.dispose()
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

    override fun handle(event: E) {
        // override in nested classes if needed
    }
}
