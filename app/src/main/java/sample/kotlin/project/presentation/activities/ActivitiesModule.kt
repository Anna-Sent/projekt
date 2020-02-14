package sample.kotlin.project.presentation.activities

import dagger.Module
import dagger.android.ContributesAndroidInjector
import sample.kotlin.project.domain.core.scopes.ActivityScope
import sample.kotlin.project.presentation.activities.main.MainActivity
import sample.kotlin.project.presentation.activities.main.MainActivityModule
import sample.kotlin.project.presentation.fragments.FragmentsModule

@Module
interface ActivitiesModule {

    @ActivityScope
    @ContributesAndroidInjector(
        modules = [
            MainActivityModule::class,
            FragmentsModule::class
        ]
    )
    fun bindMainActivity(): MainActivity
}
