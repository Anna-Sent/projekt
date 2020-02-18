package sample.kotlin.project.data.localization

import dagger.Binds
import dagger.Module
import sample.kotlin.project.domain.localization.LocalizationHelper
import javax.inject.Singleton

@Module
interface LocalizationModule {

    @Binds
    @Singleton
    fun bindLocalizationHelper(helper: LocalizationHelperImpl): LocalizationHelper
}
