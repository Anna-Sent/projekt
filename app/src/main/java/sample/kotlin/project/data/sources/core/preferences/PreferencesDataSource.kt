package sample.kotlin.project.data.sources.core.preferences

import com.f2prateek.rx.preferences2.RxSharedPreferences
import sample.kotlin.project.domain.sources.core.preferences.Preference
import sample.kotlin.project.domain.sources.core.preferences.PreferencesSource
import javax.inject.Inject

class PreferencesDataSource
@Inject constructor(
    private val rxSharedPreferencesFactory: RxSharedPreferences
) : PreferencesSource {

    override fun intValue(preference: Preference<Int>) =
        rxSharedPreferencesFactory.getInteger(preference.key, preference.defaultValue)
            .asObservable()
}
