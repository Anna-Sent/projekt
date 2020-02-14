package sample.kotlin.project.presentation.fragments

import dagger.Module
import dagger.android.ContributesAndroidInjector
import sample.kotlin.project.domain.core.scopes.ActivityScope
import sample.kotlin.project.presentation.activities.main.MainActivity
import sample.kotlin.project.presentation.fragments.search.SearchFragmentModule

@Module
interface FragmentsModule {

    @ActivityScope
    @ContributesAndroidInjector(
        modules = [
            SearchFragmentModule::class
        ]
    )
    fun contributeMainActivityFragmentInjector(): MainActivity
}
