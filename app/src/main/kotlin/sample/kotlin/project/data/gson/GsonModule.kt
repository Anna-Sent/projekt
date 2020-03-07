package sample.kotlin.project.data.gson

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import sample.kotlin.project.BuildConfig
import javax.inject.Singleton

@Module
class GsonModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
            .serializeNulls()
            .disableHtmlEscaping()
        if (BuildConfig.PRETTY_JSON) {
            gsonBuilder.setPrettyPrinting()
        }
        return gsonBuilder.create()
    }
}
