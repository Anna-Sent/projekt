package sample.kotlin.project.data.preferences

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PreferencesModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context) =
        context.getSharedPreferences("sample.kotlin.project", Context.MODE_PRIVATE)
}
