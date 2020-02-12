package sample.kotlin.project.presentation

import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import sample.kotlin.project.BuildConfig
import timber.log.Timber
import javax.inject.Inject

class SkpApp : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        setupDagger()
        setupTimber()
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    private fun setupDagger() {
        DaggerAppComponent.builder()
            .appModule(AppModule(applicationContext))
            .build()
            .inject(this)
    }

    private fun setupTimber() {
        Timber.uprootAll()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
