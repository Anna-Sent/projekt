package sample.kotlin.project.domain.sources.core.connectivity

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import io.logging.utils.DeviceUtils

val ConnectivityManager.isNetworkConnected
    get() =
        if (DeviceUtils.isAtLeastMarshmallow()) {
            val network = activeNetwork
            val capabilities = getNetworkCapabilities(network)
            capabilities != null
                    && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                    || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
        } else {
            val networkInfo = activeNetworkInfo
            networkInfo != null && networkInfo.isConnected
        }
