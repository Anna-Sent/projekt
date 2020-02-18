package sample.kotlin.project.data.localization

import sample.kotlin.project.domain.localization.LocalizationHelper
import java.util.*
import javax.inject.Inject

class LocalizationHelperImpl
@Inject constructor(
) : LocalizationHelper {

    companion object {
        private val SUPPORTED_LANGUAGE_CODES = arrayOf("en")
        private val DEFAULT_LANGUAGE_CODE = SUPPORTED_LANGUAGE_CODES[0]
    }

    override val systemLanguageCode: String get() = Locale.getDefault().language

    override val appLanguageCode get() = mapToSupportedLanguageCode(systemLanguageCode)

    private fun mapToSupportedLanguageCode(languageCode: String) =
        SUPPORTED_LANGUAGE_CODES.firstOrNull { it.equals(languageCode, ignoreCase = true) }
            ?: DEFAULT_LANGUAGE_CODE
}
