package sample.kotlin.project.presentation

import android.app.Application
import android.content.Context
import com.crashlytics.android.Crashlytics
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import io.fabric.sdk.android.Fabric
import io.logging.CrashReportSystem
import io.logging.DumpingUncaughtExceptionHandler
import io.logging.LogSystem
import io.logging.utils.DeviceUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import sample.kotlin.project.BuildConfig
import javax.inject.Inject

class App : Application(), HasAndroidInjector {

    private lateinit var logger: Logger

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    override fun onCreate() {
        super.onCreate()
        setupDagger()

        // install uncaught exception handler before initializing crash report system
        initUncaughtExceptionHandler()
        initLogging()
    }

    private fun setupDagger() {
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
}
