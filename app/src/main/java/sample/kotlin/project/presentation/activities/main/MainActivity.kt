package sample.kotlin.project.presentation.activities.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import sample.kotlin.project.R
import sample.kotlin.project.domain.stores.main.MainAction
import sample.kotlin.project.domain.stores.main.MainEvent
import sample.kotlin.project.domain.stores.main.MainState
import sample.kotlin.project.presentation.activities.main.state.MainStateParcelable
import sample.kotlin.project.presentation.core.BaseActivity
import sample.kotlin.project.presentation.fragments.search.SearchFragment
import javax.inject.Inject

class MainActivity :
    BaseActivity<MainState, MainAction, MainEvent, MainStateParcelable, MainViewModel>() {

    @Inject
    override lateinit var navigator: MainNavigator

    override fun layoutId() = R.layout.activity_main

    override fun getViewModel(provider: ViewModelProvider) =
        provider[MainViewModel::class.java]

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
}
