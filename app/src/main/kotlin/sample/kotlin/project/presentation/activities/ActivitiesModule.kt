package sample.kotlin.project.presentation.activities

import dagger.Module
import sample.kotlin.project.presentation.activities.main.MainActivityModule

@Module(
    includes = [
        MainActivityModule::class
    ]
)
interface ActivitiesModule
