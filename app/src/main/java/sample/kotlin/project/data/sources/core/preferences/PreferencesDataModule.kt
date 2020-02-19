package sample.kotlin.project.data.sources.core.preferences

import android.content.Context
import android.content.SharedPreferences
import com.f2prateek.rx.preferences2.RxSharedPreferences
import dagger.Binds
import dagger.Module
import dagger.Provides
import sample.kotlin.project.domain.sources.core.preferences.PreferencesSource
import javax.inject.Singleton

@Module(
    includes = [
        PreferencesDataModule.Providing::class
    ]
)
interface PreferencesDataModule {

    @Binds
    @Singleton
    fun bindPreferencesSource(source: PreferencesDataSource): PreferencesSource

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
