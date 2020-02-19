package sample.kotlin.project.presentation.fragments.splash

import androidx.lifecycle.ViewModelProvider
import sample.kotlin.project.R
import sample.kotlin.project.domain.stores.splash.entities.SplashAction
import sample.kotlin.project.domain.stores.splash.entities.SplashEvent
import sample.kotlin.project.domain.stores.splash.entities.SplashNavigationCommand
import sample.kotlin.project.domain.stores.splash.entities.SplashState
import sample.kotlin.project.presentation.core.BaseFragment
import sample.kotlin.project.presentation.fragments.splash.state.SplashStateParcelable

class SplashFragment : BaseFragment<SplashState, SplashAction, SplashEvent, SplashNavigationCommand,
        SplashStateParcelable, SplashViewModel>() {

    companion object {
        fun newInstance() = SplashFragment()
    }

    override fun layoutId() = R.layout.fragment_splash

    override fun provideViewModel(provider: ViewModelProvider) =
        provider[SplashViewModel::class.java]
}
