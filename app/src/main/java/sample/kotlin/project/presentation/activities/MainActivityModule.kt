package sample.kotlin.project.presentation.activities

import dagger.Module
import dagger.android.ContributesAndroidInjector
import sample.kotlin.project.domain.ActivityScope
import sample.kotlin.project.presentation.fragments.search.SearchFragmentModule

@Module
interface MainActivityModule {

    @ActivityScope
    @ContributesAndroidInjector(
        modules = [
            SearchFragmentModule::class
        ]
    )
    fun contributeSearchFragmentInjector(): MainActivity
}
