package sample.kotlin.project.domain.screens

import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.android.support.SupportAppScreen
import sample.kotlin.project.presentation.fragments.search.SearchFragment

class SearchScreen : SupportAppScreen() {

    override fun getFragment(): Fragment? {
        return SearchFragment.newInstance()
    }
}
