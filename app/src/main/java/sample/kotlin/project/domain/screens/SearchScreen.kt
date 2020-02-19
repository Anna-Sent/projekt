package sample.kotlin.project.domain.screens

import ru.terrakok.cicerone.android.support.SupportAppScreen
import sample.kotlin.project.presentation.fragments.search.SearchFragment

class SearchScreen : SupportAppScreen() {

    override fun getFragment() = SearchFragment.newInstance()
}
