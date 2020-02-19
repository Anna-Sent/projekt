package sample.kotlin.project.presentation.activities.main

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import sample.kotlin.project.R
import sample.kotlin.project.domain.sources.core.connectivity.isNetworkConnected
import sample.kotlin.project.domain.stores.main.entities.MainAction
import sample.kotlin.project.domain.stores.main.entities.MainEvent
import sample.kotlin.project.domain.stores.main.entities.MainNavigationCommand
import sample.kotlin.project.domain.stores.main.entities.MainState
import sample.kotlin.project.presentation.activities.main.state.MainStateParcelable
import sample.kotlin.project.presentation.core.BaseActivity
import javax.inject.Inject

class MainActivity : BaseActivity<MainState, MainAction, MainEvent, MainNavigationCommand,
        MainStateParcelable, MainViewModel>() {

    @Inject
    override lateinit var navigator: MainNavigator

    @Inject
    lateinit var connectivityManager: ConnectivityManager

    private lateinit var networkCallback: AppNetworkCallback

    override fun layoutId() = R.layout.activity_main

    override fun provideViewModel(provider: ViewModelProvider) =
        provider[MainViewModel::class.java]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.postAction(MainAction.NavigateToSearchAction)
        }
    }

    override fun onResume() {
        super.onResume()
        registerNetworkCallback()
    }

    override fun onPause() {
        super.onPause()
        unregisterNetworkCallback()
    }

    private fun registerNetworkCallback() {
        val isConnected = connectivityManager.isNetworkConnected
        viewModel.postAction(MainAction.NetworkConnectedChanged(isConnected))

        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        networkCallback = AppNetworkCallback {
            viewModel.postAction(MainAction.NetworkConnectedChanged(it))
        }
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    private fun unregisterNetworkCallback() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    override fun render(state: MainState) {
        // no op
    }

    override fun handleEvent(event: MainEvent) {
        // no op
    }
}
