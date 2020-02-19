package sample.kotlin.project.data.providers

import dagger.Module
import sample.kotlin.project.data.providers.connectivity.ConnectivityModule
import sample.kotlin.project.data.providers.localization.LocalizationModule
import sample.kotlin.project.data.providers.preferences.PreferencesModule
import sample.kotlin.project.data.providers.schedulers.SchedulersModule

@Module(
    includes = [
        ConnectivityModule::class,
        LocalizationModule::class,
        PreferencesModule::class,
        SchedulersModule::class
    ]
)
interface ProvidersModule
