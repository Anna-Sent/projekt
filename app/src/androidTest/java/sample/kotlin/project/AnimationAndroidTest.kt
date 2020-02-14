package sample.kotlin.project

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import sample.kotlin.project.core.AndroidTest
import sample.kotlin.project.core.AndroidTestUtils.PROPERTY_ANIMATOR_DURATION
import sample.kotlin.project.core.AndroidTestUtils.PROPERTY_TRANSITION_ANIMATION
import sample.kotlin.project.core.AndroidTestUtils.PROPERTY_WINDOW_ANIMATION
import sample.kotlin.project.core.AndroidTestUtils.getFloatSetting
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class AnimationAndroidTest : AndroidTest() {

    @Before
    @Throws(IOException::class)
    override fun before() {
        super.before()
    }

    @After
    @Throws(IOException::class)
    override fun after() {
        super.after()
    }

    @Test
    @Throws(IOException::class)
    fun animationScalesSetToZeroDuringTest() {
        val windowAnimation = getFloatSetting(PROPERTY_WINDOW_ANIMATION)
        val transitionAnimation = getFloatSetting(PROPERTY_TRANSITION_ANIMATION)
        val animatorDuration = getFloatSetting(PROPERTY_ANIMATOR_DURATION)
        Assert.assertEquals(
            PROPERTY_WINDOW_ANIMATION,
            0f,
            windowAnimation,
            0f
        )
        Assert.assertEquals(
            PROPERTY_TRANSITION_ANIMATION,
            0f,
            transitionAnimation,
            0f
        )
        Assert.assertEquals(
            PROPERTY_ANIMATOR_DURATION,
            0f,
            animatorDuration,
            0f
        )
    }
}
