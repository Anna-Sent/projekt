package sample.kotlin.project.presentation.core.views

import android.os.Parcelable
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import dagger.android.DispatchingAndroidInjector
import io.reactivex.disposables.CompositeDisposable
import sample.kotlin.project.domain.core.mvi.pojo.Action
import sample.kotlin.project.domain.core.mvi.pojo.Event
import sample.kotlin.project.domain.core.mvi.pojo.NavigationCommand
import sample.kotlin.project.domain.core.mvi.pojo.State
import javax.inject.Inject

internal class BaseView<S : State, A : Action, E : Event, NC : NavigationCommand,
    Parcel : Parcelable, VM : BaseViewModel<S, A, E, NC>>
@Inject constructor(
    internal val androidInjector: DispatchingAndroidInjector<Any>,
    internal val viewModelProviderFactory: ViewModelProvider.Factory,
    internal val stateSaver: StateSaver<S, Parcel>,
    internal val inputMethodManager: InputMethodManager
) {

    internal lateinit var viewModel: VM
    internal val disposables = CompositeDisposable()
    internal val statesDisposables = CompositeDisposable()
}
