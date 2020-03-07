package sample.kotlin.project.data.providers.preferences

import android.content.Context
import android.content.SharedPreferences
import com.f2prateek.rx.preferences2.RxSharedPreferences
import dagger.Binds
import dagger.Module
import dagger.Provides
import sample.kotlin.project.data.providers.preferences.PreferencesModule.Providing
import sample.kotlin.project.domain.providers.preferences.PreferencesProvider
import javax.inject.Singleton

@Module(
    includes = [
        Providing::class
    ]
)
interface PreferencesModule {

    @Suppress("unused")
    @Binds
    @Singleton
    fun bindPreferencesProvider(source: PreferencesDataProvider): PreferencesProvider

    @Module
    class Providing {

        companion object {
            private const val PREFERENCES_NAME = "app_preferences"
        }

        @Provides
        @Singleton
        fun provideSharedPreferences(context: Context): SharedPreferences =
            context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

        @Provides
        @Singleton
        fun provideRxSharedPreferences(sharedPreferences: SharedPreferences) =
            RxSharedPreferences.create(sharedPreferences)
    }
}
