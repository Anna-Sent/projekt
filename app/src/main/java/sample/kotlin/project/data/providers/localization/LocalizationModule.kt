package sample.kotlin.project.data.providers.localization

import dagger.Binds
import dagger.Module
import sample.kotlin.project.domain.providers.localization.LocalizationProvider
import javax.inject.Singleton

@Module
interface LocalizationModule {

    @Binds
    @Singleton
    fun bindLocalizationProvider(source: LocalizationDataProvider): LocalizationProvider
}
