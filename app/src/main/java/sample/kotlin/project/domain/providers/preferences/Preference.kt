package sample.kotlin.project.domain.providers.preferences

sealed class Preference<T>(val key: String, val defaultValue: T) {

    object Test1Preference : Preference<Int>("test1", 0)

    object Test2Preference : Preference<Boolean>("test2", false)
}
