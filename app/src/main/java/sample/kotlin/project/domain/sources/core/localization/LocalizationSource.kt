package sample.kotlin.project.domain.sources.core.localization

interface LocalizationSource {

    val systemLanguageCode: String

    val appLanguageCode: String
}
