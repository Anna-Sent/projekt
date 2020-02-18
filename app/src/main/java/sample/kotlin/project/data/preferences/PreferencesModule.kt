package sample.kotlin.project.data.preferences

import android.content.Context
import android.content.SharedPreferences
import com.f2prateek.rx.preferences2.RxSharedPreferences
import dagger.Module
import dagger.Provides
import sample.kotlin.project.data.preferences.PreferencesConstants.PREFERENCES_NAME
import javax.inject.Singleton

@Module
class PreferencesModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)


    @Singleton
    @Provides
    fun provideRxSharedPreferences(sharedPreferences: SharedPreferences) =
        RxSharedPreferences.create(sharedPreferences)
}
