package sample.kotlin.project.presentation.activities

import android.os.Bundle
import dagger.android.HasAndroidInjector
import sample.kotlin.project.R
import sample.kotlin.project.presentation.core.BaseActivity
import sample.kotlin.project.presentation.fragments.search.SearchFragment

class MainActivity : BaseActivity(), HasAndroidInjector {

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
