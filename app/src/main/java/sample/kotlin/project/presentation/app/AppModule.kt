package sample.kotlin.project.presentation.app

import android.content.Context
import android.net.ConnectivityManager
import android.view.inputmethod.InputMethodManager
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
class AppModule(private val app: App) {

    @Provides
    @Singleton
    fun provideContext(): Context = app.applicationContext

    @Provides
    @Singleton
    fun provideApp() = app

    @Provides
    @Singleton
    fun provideRefWatcher() = app.refWatcher

    @Provides
    @Singleton
    fun provideLogger() = app.logger

    @Provides
    @Singleton
    fun provideAppRouter() = app.router

    @Provides
    @Singleton
    fun provideNavigatorHolder() = app.navigatorHolder

    @Provides
    @Singleton
    fun provideInputMethodManager() =
        app.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    @Provides
    @Singleton
    fun provideConnectivityManager() =
        app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
}
