package sample.kotlin.project.data.providers.localization

import dagger.Binds
import dagger.Module
import sample.kotlin.project.domain.sources.core.localization.LocalizationProvider
import javax.inject.Singleton

@Module
interface LocalizationModule {

    @Binds
    @Singleton
    fun bindLocalizationSource(source: LocalizationDataProvider): LocalizationProvider
}
