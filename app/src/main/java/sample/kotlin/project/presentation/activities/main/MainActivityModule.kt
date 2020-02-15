package sample.kotlin.project.presentation.activities.main

import dagger.Module
import dagger.Provides
import sample.kotlin.project.R

@Module
class MainActivityModule {

    @Provides
    internal fun provideNavigator(activity: MainActivity) =
        MainNavigator(activity, R.id.container)
}
