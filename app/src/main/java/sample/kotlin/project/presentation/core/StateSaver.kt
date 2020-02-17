package sample.kotlin.project.presentation.core

import android.os.Bundle
import android.os.Parcelable
import sample.kotlin.project.domain.core.Mapper
import sample.kotlin.project.domain.core.mvi.State
import javax.inject.Inject

class StateSaver<S : State, Parcel : Parcelable>
@Inject constructor(
    private val toParcelableMapper: Mapper<S, Parcel>,
    private val fromParcelableMapper: Mapper<Parcel, S>
) {

    companion object {
        private const val BUNDLE_SAVED_STATE = "BUNDLE_SAVED_STATE"
    }

    var state: S? = null
        internal set

    fun saveInstanceState(outState: Bundle) =
        state?.let {
            outState.putParcelable(BUNDLE_SAVED_STATE, toParcelableMapper.map(it))
        }

    fun restoreState(savedState: Bundle?) {
        state = savedState?.getParcelable<Parcel>(BUNDLE_SAVED_STATE)
            ?.let(fromParcelableMapper::map)
    }

    fun stateOrDefault(defaultStateProvider: () -> S) =
        state ?: defaultStateProvider.invoke()
}
