package sample.kotlin.project.data.network.http

import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.ResponseBody.Companion.toResponseBody
import java.net.HttpURLConnection

class FakeInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain) =
        when {

            chain.request().url.toUri().path.endsWith("search/repositories") ->
                successResponse(chain, SEARCH_REPOSITORIES_RESPONSE)

            else -> chain.proceed(chain.request())
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
        private const val SEARCH_REPOSITORIES_RESPONSE = """
[{
	"id": 1296269,
	"node_id": "MDEwOlJlcG9zaXRvcnkxMjk2MjY5",
	"name": "Hello-World",
	"full_name": "octocat/Hello-World",
	"private": false,
	"html_url": "https://github.com/octocat/Hello-World",
	"description": "This your first repo!",
	"fork": false,
	"languages_url": "http://api.github.com/repos/octocat/Hello-World/languages",
	"stargazers_count": 80,
	"watchers_count": 80,
	"pushed_at": "2011-01-26T19:06:43Z",
	"created_at": "2011-01-26T19:01:12Z",
	"updated_at": "2011-01-26T19:14:43Z",
	"subscribers_count": 42
}]
"""
    }
}
