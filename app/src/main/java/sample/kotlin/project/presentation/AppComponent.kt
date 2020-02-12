package sample.kotlin.project.presentation

import android.content.Context
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.android.support.AndroidSupportInjectionModule
import sample.kotlin.project.data.DataModule
import sample.kotlin.project.presentation.activities.MainActivityModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class
    ]
)
interface AppComponent {

    fun inject(app: SkpApp)
}

@Module(
    includes = [
        MainActivityModule::class, DataModule::class
    ]
)
class AppModule(private val context: Context) {

    @Provides
    fun provideContext() = context
}
