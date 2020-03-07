package sample.kotlin.project.presentation.fragments.splash

import androidx.lifecycle.ViewModelProvider
import sample.kotlin.project.R
import sample.kotlin.project.domain.stores.splash.pojo.SplashAction
import sample.kotlin.project.domain.stores.splash.pojo.SplashEvent
import sample.kotlin.project.domain.stores.splash.pojo.SplashNavigationCommand
import sample.kotlin.project.domain.stores.splash.pojo.SplashState
import sample.kotlin.project.presentation.core.views.BaseFragment

class SplashFragment : BaseFragment<SplashState, SplashAction, SplashEvent, SplashNavigationCommand,
    SplashStateParcelable, SplashViewModel>() {

    companion object {
        fun newInstance() = SplashFragment()
    }

    override val layoutId = R.layout.fragment_splash

    override fun provideViewModel(provider: ViewModelProvider) =
        provider[SplashViewModel::class.java]
}
