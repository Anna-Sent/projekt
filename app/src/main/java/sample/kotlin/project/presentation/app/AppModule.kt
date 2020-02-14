package sample.kotlin.project.presentation.app

import android.content.Context
import android.net.ConnectivityManager
import dagger.Module
import dagger.Provides
import sample.kotlin.project.data.DataModule
import sample.kotlin.project.domain.DomainModule
import sample.kotlin.project.presentation.PresentationModule
import javax.inject.Singleton

@Module(
    includes = [
        DataModule::class,
        DomainModule::class,
        PresentationModule::class
    ]
)
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideContext() = context

    @Provides
    @Singleton
    fun provideApp(app: App) = app

    @Provides
    @Singleton
    fun provideRefWatcher(app: App) = app.refWatcher

    @Provides
    @Singleton
    fun provideLogger(app: App) = app.logger

    @Provides
    @Singleton
    fun provideConnectivityManager(context: Context) =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
}
