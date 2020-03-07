package sample.kotlin.project.data.network.http

import android.content.Context
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.nio.charset.StandardCharsets
import javax.inject.Inject

class FakeInterceptor
@Inject constructor(
    private val context: Context
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain) =
        when {

            chain.request().url.toUri().path.endsWith("search/repositories") ->
                successResponse(chain, loadFromAssets("response.json"))

            else -> chain.proceed(chain.request())
        }

    private fun loadFromAssets(fileName: String): String {
        BufferedReader(InputStreamReader(context.assets.open(fileName), StandardCharsets.UTF_8))
            .use { reader ->
                val sb = StringBuilder()
                var line = reader.readLine()
                while (line != null) {
                    sb.append(line)
                    line = reader.readLine()
                }
                return sb.toString()
            }
    }

    private fun successResponse(chain: Interceptor.Chain, responseString: String) =
        chain.proceed(chain.request())
            .newBuilder()
            .code(HttpURLConnection.HTTP_OK)
            .protocol(Protocol.HTTP_1_1)
            .message("OK")
            .body(responseString.toResponseBody(MEDIA_TYPE))
            .addHeader("Content-Type", "application/json")
            .build()

    companion object {
        private val MEDIA_TYPE = "application/json".toMediaType()
    }
}
