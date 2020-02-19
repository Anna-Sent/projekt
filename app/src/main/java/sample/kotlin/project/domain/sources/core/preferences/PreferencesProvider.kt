package sample.kotlin.project.domain.sources.core.preferences

import io.reactivex.Observable

interface PreferencesProvider {

    fun intValue(preference: Preference<Int>): Observable<Int>
}
