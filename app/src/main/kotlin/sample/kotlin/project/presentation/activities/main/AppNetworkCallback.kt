package sample.kotlin.project.presentation.activities.main

import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import io.logging.utils.DeviceUtils.isAtLeastMarshmallow
import org.slf4j.LoggerFactory
import java.util.HashSet

internal class AppNetworkCallback(
    private val connectedSetter: (Boolean) -> Unit
) : NetworkCallback() {

    private val logger = LoggerFactory.getLogger(toString())
    private val netIds = HashSet<String>()

    override fun onAvailable(network: Network) {
        logger.debug("onAvailable: $network")
        onNetworkConnected(network)
    }

    override fun onLosing(network: Network, maxMsToLive: Int) {
        logger.debug("onLosing: $network $maxMsToLive")
    }

    override fun onLost(network: Network) {
        logger.debug("onLost: $network")
        onNetworkDisconnected(network)
    }

    override fun onUnavailable() {
        logger.debug("onUnavailable")
    }

    private fun onNetworkConnected(network: Network) {
        netIds.add(netId(network))
        onNetworkChanged()
    }

    private fun onNetworkDisconnected(network: Network) {
        netIds.remove(netId(network))
        onNetworkChanged()
    }

    private fun onNetworkChanged() {
        logger.debug("connected size: ${netIds.size}")
        connectedSetter.invoke(netIds.isNotEmpty())
    }

    companion object {
        private fun netId(network: Network) =
            if (isAtLeastMarshmallow()) network.networkHandle.toString() else network.toString()
    }
}
