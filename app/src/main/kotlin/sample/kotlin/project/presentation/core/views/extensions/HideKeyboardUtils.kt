package sample.kotlin.project.presentation.core.views.extensions

import android.app.Activity
import android.os.Parcelable
import android.view.inputmethod.InputMethodManager
import sample.kotlin.project.domain.core.mvi.pojo.Action
import sample.kotlin.project.domain.core.mvi.pojo.Event
import sample.kotlin.project.domain.core.mvi.pojo.NavigationCommand
import sample.kotlin.project.domain.core.mvi.pojo.State
import sample.kotlin.project.presentation.core.views.BaseActivity
import sample.kotlin.project.presentation.core.views.BaseDialogFragment
import sample.kotlin.project.presentation.core.views.BaseFragment
import sample.kotlin.project.presentation.core.views.BaseViewModel

fun <S : State, A : Action, E : Event, NC : NavigationCommand,
    Parcel : Parcelable, VM : BaseViewModel<S, A, E, NC>>
    BaseActivity<S, A, E, NC, Parcel, VM>.hideKeyboard() =
    hideKeyboard(
        baseView.inputMethodManager,
        this
    )

fun <S : State, A : Action, E : Event, NC : NavigationCommand,
    Parcel : Parcelable, VM : BaseViewModel<S, A, E, NC>>
    BaseFragment<S, A, E, NC, Parcel, VM>.hideKeyboard() =
    hideKeyboard(
        baseView.inputMethodManager,
        requireActivity()
    )

fun <S : State, A : Action, E : Event, NC : NavigationCommand,
    Parcel : Parcelable, VM : BaseViewModel<S, A, E, NC>>
    BaseDialogFragment<S, A, E, NC, Parcel, VM>.hideKeyboard() =
    hideKeyboard(
        baseView.inputMethodManager,
        requireActivity()
    )

private fun hideKeyboard(
    inputMethodManager: InputMethodManager,
    activity: Activity
) {
    val activityFocus = activity.currentFocus
    if (activityFocus != null) {
        inputMethodManager.hideSoftInputFromWindow(activityFocus.windowToken, 0)
    }
}
