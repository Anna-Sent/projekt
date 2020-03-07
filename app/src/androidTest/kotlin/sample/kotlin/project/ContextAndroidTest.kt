package sample.kotlin.project

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import sample.kotlin.project.core.AndroidTestUtils.appContext
import sample.kotlin.project.core.AndroidTestUtils.testContext

@RunWith(AndroidJUnit4::class)
class ContextAndroidTest {

    companion object {
        private const val PACKAGE_NAME = "sample.kotlin.project.debug"
    }

    @Test
    fun checkContext() {
        Assert.assertEquals(
            PACKAGE_NAME,
            appContext.packageName
        )
        Assert.assertEquals(
            "$PACKAGE_NAME.test",
            testContext.packageName
        )
    }
}
