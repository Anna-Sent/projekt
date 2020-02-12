package sample.kotlin.project.presentation.core

import android.os.Bundle
import android.os.Parcelable
import sample.kotlin.project.domain.FragmentScope
import sample.kotlin.project.domain.Mapper
import sample.kotlin.project.domain.mvi.State
import javax.inject.Inject

@FragmentScope
class StateSaver<S : State, Parcel : Parcelable>
@Inject constructor(
    private val toParcelableMapper: Mapper<S, Parcel>,
    private val fromParcelableMapper: Mapper<Parcel, S>
) {

    companion object {
        private const val BUNDLE_SAVED_STATE = "BUNDLE_SAVED_STATE"
    }

    private var state: S? = null

    fun setState(state: S?) {
        this.state = state
    }

    fun saveInstanceState(outState: Bundle) =
        state?.let {
            outState.putParcelable(BUNDLE_SAVED_STATE, toParcelableMapper.map(it))
        }

    fun restoreState(savedState: Bundle?) {
        state = savedState?.getParcelable<Parcel>(BUNDLE_SAVED_STATE)
            ?.let(fromParcelableMapper::map)
    }

    fun getStateOrDefault(defaultStateProvider: () -> S) =
        state ?: defaultStateProvider.invoke()
}
