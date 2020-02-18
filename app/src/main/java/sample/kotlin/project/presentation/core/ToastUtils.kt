package sample.kotlin.project.presentation.core

import android.content.Context
import android.os.Parcelable
import android.widget.Toast
import sample.kotlin.project.domain.core.mvi.Action
import sample.kotlin.project.domain.core.mvi.Event
import sample.kotlin.project.domain.core.mvi.State

fun <S : State, A : Action, E : Event, Parcel : Parcelable, VM : BaseViewModel<S, A, E>>
        BaseActivity<S, A, E, Parcel, VM>.toast(message: String) =
    toast(this, message)

fun <S : State, A : Action, E : Event, Parcel : Parcelable, VM : BaseViewModel<S, A, E>>
        BaseFragment<S, A, E, Parcel, VM>.toast(message: String) =
    toast(requireContext(), message)

fun <S : State, A : Action, E : Event, Parcel : Parcelable, VM : BaseViewModel<S, A, E>>
        BaseDialogFragment<S, A, E, Parcel, VM>.toast(message: String) =
    toast(requireContext(), message)

private fun toast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}
