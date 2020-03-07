package sample.kotlin.project.domain.providers.preferences

import io.reactivex.Observable

interface PreferencesProvider {

    fun intValue(preference: Preference<Int>): Observable<Int>
}
