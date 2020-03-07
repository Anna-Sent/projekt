package sample.kotlin.project.data.network.http

import okhttp3.Interceptor

class GeneralInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain) = chain.proceed(chain.request())
}
