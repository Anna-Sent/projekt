package sample.kotlin.project.data.providers.preferences

import com.f2prateek.rx.preferences2.RxSharedPreferences
import sample.kotlin.project.domain.sources.core.preferences.Preference
import sample.kotlin.project.domain.sources.core.preferences.PreferencesProvider
import javax.inject.Inject

class PreferencesDataProvider
@Inject constructor(
    private val rxSharedPreferencesFactory: RxSharedPreferences
) : PreferencesProvider {

    override fun intValue(preference: Preference<Int>) =
        rxSharedPreferencesFactory.getInteger(preference.key, preference.defaultValue)
            .asObservable()
}
