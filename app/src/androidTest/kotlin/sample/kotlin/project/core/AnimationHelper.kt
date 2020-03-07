package sample.kotlin.project.core

import sample.kotlin.project.core.AndroidTestUtils.PROPERTY_ANIMATOR_DURATION
import sample.kotlin.project.core.AndroidTestUtils.PROPERTY_TRANSITION_ANIMATION
import sample.kotlin.project.core.AndroidTestUtils.PROPERTY_WINDOW_ANIMATION
import sample.kotlin.project.core.AndroidTestUtils.floatSetting
import sample.kotlin.project.core.AndroidTestUtils.putFloatSetting
import java.io.IOException

internal class AnimationHelper {

    private var windowAnimation = 0f
    private var transitionAnimation = 0f
    private var animatorDuration = 0f

    @Throws(IOException::class)
    fun setAnimationsEnabled(enabled: Boolean) {
        if (!enabled) {
            saveAnimationSettings()
        }
        putFloatSetting(PROPERTY_WINDOW_ANIMATION, if (enabled) windowAnimation else 0f)
        putFloatSetting(PROPERTY_TRANSITION_ANIMATION, if (enabled) transitionAnimation else 0f)
        putFloatSetting(PROPERTY_ANIMATOR_DURATION, if (enabled) animatorDuration else 0f)
    }

    @Throws(IOException::class)
    private fun saveAnimationSettings() {
        windowAnimation = floatSetting(PROPERTY_WINDOW_ANIMATION)
        transitionAnimation = floatSetting(PROPERTY_TRANSITION_ANIMATION)
        animatorDuration = floatSetting(PROPERTY_ANIMATOR_DURATION)
    }
}
