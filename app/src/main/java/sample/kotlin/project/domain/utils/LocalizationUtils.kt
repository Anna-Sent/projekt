package sample.kotlin.project.domain.utils

import java.util.*

object LocalizationUtils {

    private val SUPPORTED_LANGUAGE_CODES = arrayOf("en")
    private val DEFAULT_LANGUAGE_CODE = SUPPORTED_LANGUAGE_CODES[0]

    val systemLanguageCode: String get() = Locale.getDefault().language

    val appLanguageCode get() = mapToSupportedLanguageCode(systemLanguageCode)

    private fun mapToSupportedLanguageCode(languageCode: String): String =
        SUPPORTED_LANGUAGE_CODES.firstOrNull { it.equals(languageCode, ignoreCase = true) }
            ?: DEFAULT_LANGUAGE_CODE
}
