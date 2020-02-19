package sample.kotlin.project.data.sources.core.localization

import dagger.Binds
import dagger.Module
import sample.kotlin.project.domain.sources.core.localization.LocalizationSource
import javax.inject.Singleton

@Module
interface LocalizationModule {

    @Binds
    @Singleton
    fun bindLocalizationSource(source: LocalizationDataSource): LocalizationSource
}
