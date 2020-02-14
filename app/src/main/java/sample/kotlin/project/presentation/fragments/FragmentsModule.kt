package sample.kotlin.project.presentation.fragments

import dagger.Module
import sample.kotlin.project.presentation.fragments.search.SearchFragmentModule

@Module(
    includes = [
        SearchFragmentModule::class
    ]
)
interface FragmentsModule
