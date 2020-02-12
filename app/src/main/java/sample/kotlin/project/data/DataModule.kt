package sample.kotlin.project.data

import dagger.Module

@Module(
    includes = [
        PreferencesModule::class
    ]
)
interface DataModule
