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

    private val INSTRUMENTATION = InstrumentationRegistry.getInstrumentation()
    private val uiAutomation = INSTRUMENTATION.uiAutomation
    val testContext: Context = INSTRUMENTATION.context
    val appContext: Context = ApplicationProvider.getApplicationContext()

    @Throws(IOException::class)
    fun getFloatSetting(property: String): Float {
        val command = "settings get global $property"
        val fileDescriptor = uiAutomation.executeShellCommand(command)
        val fileReader = BufferedReader(FileReader(fileDescriptor.fileDescriptor))

        var value: Float? = null
        try {
            var line = fileReader.readLine()
            while (line != null) {
                if (value == null) {
                    value = line.toFloat()
                }
                line = fileReader.readLine()
            }
        } finally {
            fileReader.close()
        }
        return requireNotNull(value) { "Failed to read property $property" }
    }

    @Throws(IOException::class)
    fun putFloatSetting(property: String, value: Float) {
        val command = "settings put global $property $value"
        val fileDescriptor = uiAutomation.executeShellCommand(command)
        val fileStream = FileInputStream(fileDescriptor.fileDescriptor)

        try {
            val buffer = ByteArray(1024)
            while (fileStream.read(buffer) > 0) {
            }
        } finally {
            fileStream.close()
        }
    }
}
