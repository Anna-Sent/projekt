package sample.kotlin.project.data.network.http

import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.CertificatePinner
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.helpers.NOPLogger
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import sample.kotlin.project.BuildConfig
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class HttpModule {

    companion object {
        private const val TIMEOUT_SECS = 20L
    }

    @Provides
    @Singleton
    @Named("HttpLogger")
    fun provideLogger(): Logger =
        if (BuildConfig.PRINT_HTTP_LOGS) LoggerFactory.getLogger("http")
        else NOPLogger.NOP_LOGGER

    @Provides
    @Singleton
    fun provideHttpLogger(@Named("HttpLogger") logger: Logger) =
        object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                logger.debug(message)
            }
        }

    @Provides
    @Singleton
    @Named("LoggingInterceptor")
    fun provideLoggingInterceptor(httpLogger: HttpLoggingInterceptor.Logger): Interceptor =
        HttpLoggingInterceptor(httpLogger).apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    @Named("AppInterceptor")
    fun provideAppInterceptor(context: Context) =
        if (BuildConfig.USE_FAKE_INTERCEPTOR) FakeInterceptor(context)
        else GeneralInterceptor()

    @Provides
    @Singleton
    fun provideCertificatePinner() =
        if (BuildConfig.USE_CERTIFICATE_PINNING)
            CertificatePinner.Builder()
                .add(BuildConfig.GITHUB_API_HOST, BuildConfig.GITHUB_API_PIN)
                .build()
        else CertificatePinner.DEFAULT

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @Named("LoggingInterceptor") loggingInterceptor: Interceptor,
        @Named("AppInterceptor") appInterceptor: Interceptor,
        certificatePinner: CertificatePinner
    ) = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(appInterceptor)
        .certificatePinner(certificatePinner)
        .connectTimeout(TIMEOUT_SECS, TimeUnit.SECONDS)
        .writeTimeout(TIMEOUT_SECS, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT_SECS, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.GITHUB_API_URL)
        .client(okHttpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(gsonConverterFactory)
        .build()
}
