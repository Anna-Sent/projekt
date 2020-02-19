package sample.kotlin.project.presentation

import dagger.Module
import sample.kotlin.project.presentation.activities.ActivitiesModule
import sample.kotlin.project.presentation.fragments.FragmentsModule

@Module(
    includes = [
        ActivitiesModule::class,
        FragmentsModule::class
    ]
)
interface PresentationModule
