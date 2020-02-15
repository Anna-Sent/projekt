package sample.kotlin.project.presentation.app

import android.app.Application
import android.content.Context
import com.crashlytics.android.Crashlytics
import com.gu.toolargetool.TooLargeTool
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import io.fabric.sdk.android.Fabric
import io.logging.CrashReportSystem
import io.logging.DumpingUncaughtExceptionHandler
import io.logging.LogSystem
import io.logging.utils.DeviceUtils
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import net.danlew.android.joda.JodaTimeAndroid
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import sample.kotlin.project.BuildConfig
import javax.inject.Inject

class App : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    lateinit var logger: Logger
    lateinit var refWatcher: RefWatcher
    lateinit var cicerone: Cicerone<AppRouter>
    val navigatorHolder: NavigatorHolder get() = cicerone.navigatorHolder
    val router: AppRouter get() = cicerone.router

    override fun onCreate() {
        super.onCreate()
        initDi()

        // init memory analyzer start
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not showActivities your app in this process.
            return
        }

        refWatcher = LeakCanary.install(this)
        // init memory analyzer finish

        // install uncaught exception handler before initializing crash report system
        initUncaughtExceptionHandler()
        initLogging()
        initLibraries()
        initNavigation()
    }

    private fun initDi() {
        DaggerAppComponent.builder()
            .appModule(AppModule(applicationContext))
            .build()
            .inject(this)
    }

    private fun initUncaughtExceptionHandler() {
        val dumpOutOfMemory = BuildConfig.DUMP_OUT_OF_MEMORY
                && DeviceUtils.isExternalStorageWritable()
        if (!dumpOutOfMemory) {
            return
        }
        val externalDir = getExternalFilesDir(null) ?: return
        val directoryPath = externalDir.absolutePath
        val handler: Thread.UncaughtExceptionHandler =
            DumpingUncaughtExceptionHandler(directoryPath)
        Thread.setDefaultUncaughtExceptionHandler(handler)
    }

    private fun initLogging() {
        LogSystem.initLogger(this, LogSystem.Config.builder()
            .tag(BuildConfig.LOG_TAG)
            .enableCrs(BuildConfig.ENABLE_CRS)
            .printLogsToFile(BuildConfig.PRINT_LOGS_TO_FILE)
            .printLogsToCrs(BuildConfig.PRINT_LOGS_TO_CRS)
            .printLogsToLogcat(BuildConfig.PRINT_LOGS_TO_LOGCAT)
            .printSensitiveData(BuildConfig.PRINT_SENSITIVE_DATA)
            .build(),
            object : CrashReportSystem {
                override fun init(
                    appContext: Context,
                    enableCrs: Boolean
                ) {
                    if (BuildConfig.ENABLE_CRS) {
                        Fabric.with(appContext, Crashlytics())
                    }
                }

                override fun report(throwable: Throwable) {
                    Crashlytics.logException(throwable)
                }

                override fun log(msg: String) {
                    Crashlytics.log(msg)
                }
            })
        logger = LoggerFactory.getLogger(toString())
        logger.debug("onCreate")
    }

    private fun initLibraries() {
        JodaTimeAndroid.init(this)

        RxJavaPlugins.setErrorHandler {
            var throwable = it
            while (throwable is UndeliverableException) {
                throwable = throwable.cause
            }
            logger.warn("Undeliverable error received", throwable)
        }

        if (BuildConfig.DEBUG) {
            TooLargeTool.startLogging(this)
        }
    }

    private fun initNavigation() {
        cicerone = Cicerone.create(AppRouter())
    }
}
