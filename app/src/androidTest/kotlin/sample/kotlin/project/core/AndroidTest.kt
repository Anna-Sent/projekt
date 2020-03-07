package sample.kotlin.project.core

import androidx.annotation.CallSuper
import java.io.IOException

@Suppress("UnnecessaryAbstractClass")
abstract class AndroidTest {

    private val animationHelper = AnimationHelper()

    @CallSuper
    @Throws(IOException::class)
    open fun before() {
        animationHelper.setAnimationsEnabled(false)
    }

    @CallSuper
    @Throws(IOException::class)
    open fun after() {
        animationHelper.setAnimationsEnabled(true)
    }
}
