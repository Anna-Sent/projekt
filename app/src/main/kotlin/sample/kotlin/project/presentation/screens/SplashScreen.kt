package sample.kotlin.project.presentation.screens

import ru.terrakok.cicerone.android.support.SupportAppScreen
import sample.kotlin.project.presentation.fragments.splash.SplashFragment

class SplashScreen : SupportAppScreen() {

    override fun getFragment() = SplashFragment.newInstance()
}
