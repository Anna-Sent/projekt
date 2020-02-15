package sample.kotlin.project.presentation.activities.main

import android.os.Bundle
import sample.kotlin.project.R
import sample.kotlin.project.presentation.core.BaseActivity
import sample.kotlin.project.presentation.fragments.search.SearchFragment
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    override lateinit var navigator: MainNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container,
                    SearchFragment.newInstance()
                )
                .commitNow()
        }
    }

    override fun layoutId() = R.layout.activity_main
}
