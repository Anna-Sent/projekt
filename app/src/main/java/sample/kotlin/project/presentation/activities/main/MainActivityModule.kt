package sample.kotlin.project.presentation.activities.main

import dagger.Module
import sample.kotlin.project.presentation.fragments.FragmentsModule

@Module(
    includes = [
        FragmentsModule::class
    ]
)
interface MainActivityModule
