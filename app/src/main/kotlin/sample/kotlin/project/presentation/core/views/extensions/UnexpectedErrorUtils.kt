package sample.kotlin.project.presentation.core.views.extensions

import android.os.Parcelable
import sample.kotlin.project.domain.core.mvi.pojo.Action
import sample.kotlin.project.domain.core.mvi.pojo.Event
import sample.kotlin.project.domain.core.mvi.pojo.NavigationCommand
import sample.kotlin.project.domain.core.mvi.pojo.State
import sample.kotlin.project.domain.core.mvi.unexpectedError
import sample.kotlin.project.presentation.core.views.BaseActivity
import sample.kotlin.project.presentation.core.views.BaseDialogFragment
import sample.kotlin.project.presentation.core.views.BaseFragment
import sample.kotlin.project.presentation.core.views.BaseViewModel

fun <S : State, A : Action, E : Event, NC : NavigationCommand,
    Parcel : Parcelable, VM : BaseViewModel<S, A, E, NC>>
    BaseActivity<S, A, E, NC, Parcel, VM>.unexpectedError(throwable: Throwable) =
    unexpectedError(logger, throwable)

fun <S : State, A : Action, E : Event, NC : NavigationCommand,
    Parcel : Parcelable, VM : BaseViewModel<S, A, E, NC>>
    BaseFragment<S, A, E, NC, Parcel, VM>.unexpectedError(throwable: Throwable) =
    unexpectedError(logger, throwable)

fun <S : State, A : Action, E : Event, NC : NavigationCommand,
    Parcel : Parcelable, VM : BaseViewModel<S, A, E, NC>>
    BaseDialogFragment<S, A, E, NC, Parcel, VM>.unexpectedError(throwable: Throwable) =
    unexpectedError(logger, throwable)

fun <S : State, A : Action, E : Event, NC : NavigationCommand>
    BaseViewModel<S, A, E, NC>.unexpectedError(throwable: Throwable) =
    unexpectedError(logger, throwable)
