package sample.kotlin.project.domain.core.mvi

interface MviView<S : State, E : Event> {

    fun render(state: S)

    fun handleEvent(event: E)
}
