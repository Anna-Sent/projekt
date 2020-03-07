package sample.kotlin.project.domain.providers.localization

interface LocalizationProvider {

    val systemLanguageCode: String

    val appLanguageCode: String
}
