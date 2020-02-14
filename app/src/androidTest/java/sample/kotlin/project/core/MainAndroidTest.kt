package sample.kotlin.project.core

import androidx.annotation.CallSuper
import androidx.test.core.app.ActivityScenario
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import sample.kotlin.project.presentation.activities.MainActivity
import java.io.IOException

abstract class MainAndroidTest : AndroidTest() {

    @Rule
    val activityTestRule = ActivityTestRule(
        MainActivity::class.java
    )

    @CallSuper
    @Throws(IOException::class)
    override fun before() {
        super.before()
        ActivityScenario.launch(MainActivity::class.java)
    }
}
