package sample.kotlin.project.presentation.core.utils

import android.content.Context
import android.os.Parcelable
import android.widget.Toast
import sample.kotlin.project.domain.core.mvi.entities.Action
import sample.kotlin.project.domain.core.mvi.entities.Event
import sample.kotlin.project.domain.core.mvi.entities.NavigationCommand
import sample.kotlin.project.domain.core.mvi.entities.State
import sample.kotlin.project.presentation.core.views.BaseActivity
import sample.kotlin.project.presentation.core.views.BaseDialogFragment
import sample.kotlin.project.presentation.core.views.BaseFragment
import sample.kotlin.project.presentation.core.views.BaseViewModel

fun <S : State, A : Action, E : Event, NC : NavigationCommand,
        Parcel : Parcelable, VM : BaseViewModel<S, A, E, NC>>
        BaseActivity<S, A, E, NC, Parcel, VM>.toast(message: String) =
    toast(this, message)

fun <S : State, A : Action, E : Event, NC : NavigationCommand,
        Parcel : Parcelable, VM : BaseViewModel<S, A, E, NC>>
        BaseFragment<S, A, E, NC, Parcel, VM>.toast(message: String) =
    toast(requireContext(), message)

fun <S : State, A : Action, E : Event, NC : NavigationCommand,
        Parcel : Parcelable, VM : BaseViewModel<S, A, E, NC>>
        BaseDialogFragment<S, A, E, NC, Parcel, VM>.toast(message: String) =
    toast(requireContext(), message)

private fun toast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}
