package sample.kotlin.project.presentation.fragments.search

import sample.kotlin.project.domain.search.SearchAction
import sample.kotlin.project.domain.search.SearchEvent
import sample.kotlin.project.domain.search.SearchState
import sample.kotlin.project.domain.search.SearchStore
import sample.kotlin.project.presentation.core.BaseViewModel
import javax.inject.Inject

class SearchViewModel @Inject constructor(store: SearchStore) :
    BaseViewModel<SearchState, SearchAction, SearchEvent>(store)
