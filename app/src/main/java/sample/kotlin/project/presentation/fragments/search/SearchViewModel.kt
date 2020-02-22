package sample.kotlin.project.presentation.fragments.search

import sample.kotlin.project.domain.stores.search.SearchStore
import sample.kotlin.project.domain.stores.search.pojo.SearchAction
import sample.kotlin.project.domain.stores.search.pojo.SearchEvent
import sample.kotlin.project.domain.stores.search.pojo.SearchNavigationCommand
import sample.kotlin.project.domain.stores.search.pojo.SearchState
import sample.kotlin.project.presentation.core.views.BaseViewModel
import javax.inject.Inject

class SearchViewModel
@Inject constructor(
    store: SearchStore
) : BaseViewModel<SearchState, SearchAction, SearchEvent, SearchNavigationCommand>(store)
