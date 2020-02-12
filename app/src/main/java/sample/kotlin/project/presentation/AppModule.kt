package sample.kotlin.project.presentation

import android.content.Context
import dagger.Module
import dagger.Provides
import sample.kotlin.project.data.DataModule
import sample.kotlin.project.presentation.activities.MainActivityModule

@Module(
    includes = [
        DataModule::class,
        MainActivityModule::class
    ]
)
class AppModule(private val context: Context) {

    @Provides
    fun provideContext() = context
}
