package sample.kotlin.project.presentation

import dagger.Module
import sample.kotlin.project.presentation.activities.ActivitiesModule

@Module(
    includes = [
        ActivitiesModule::class
    ]
)
interface PresentationModule
