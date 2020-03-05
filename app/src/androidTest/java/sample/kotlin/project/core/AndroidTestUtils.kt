package sample.kotlin.project.core

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileReader
import java.io.IOException

object AndroidTestUtils {

    const val PROPERTY_WINDOW_ANIMATION = "window_animation_scale"
    const val PROPERTY_TRANSITION_ANIMATION = "transition_animation_scale"
    const val PROPERTY_ANIMATOR_DURATION = "animator_duration_scale"

    private val instrumentation = InstrumentationRegistry.getInstrumentation()
    private val uiAutomation = instrumentation.uiAutomation
    val testContext: Context = instrumentation.context
    val appContext: Context = ApplicationProvider.getApplicationContext()

    @Throws(IOException::class)
    fun floatSetting(property: String): Float {
        val command = "settings get global $property"
        val fileDescriptor = uiAutomation.executeShellCommand(command)
        val fileReader = BufferedReader(FileReader(fileDescriptor.fileDescriptor))

        var value: Float? = null
        fileReader.use {
            var line = it.readLine()
            while (line != null) {
                if (value == null) {
                    value = line.toFloat()
                }
                line = it.readLine()
            }
        }
        return requireNotNull(value) { "Failed to read property $property" }
    }

    @Throws(IOException::class)
    fun putFloatSetting(property: String, value: Float) {
        val command = "settings put global $property $value"
        val fileDescriptor = uiAutomation.executeShellCommand(command)
        val fileStream = FileInputStream(fileDescriptor.fileDescriptor)

        fileStream.use {
            val buffer = ByteArray(1024)
            while (it.read(buffer) > 0) {
            }
        }
    }
}
