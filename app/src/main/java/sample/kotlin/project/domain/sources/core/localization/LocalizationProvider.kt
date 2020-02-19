package sample.kotlin.project.domain.sources.core.localization

interface LocalizationProvider {

    val systemLanguageCode: String

    val appLanguageCode: String
}
