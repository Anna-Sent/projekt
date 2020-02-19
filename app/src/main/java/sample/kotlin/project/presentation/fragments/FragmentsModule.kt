package sample.kotlin.project.presentation.fragments

import dagger.Module
import sample.kotlin.project.presentation.fragments.search.SearchFragmentModule
import sample.kotlin.project.presentation.fragments.splash.SplashFragmentModule

@Module(
    includes = [
        SearchFragmentModule::class,
        SplashFragmentModule::class
    ]
)
interface FragmentsModule
