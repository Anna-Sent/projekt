package sample.kotlin.project.domain.core

interface Mapper<From, To> {

    fun map(from: From): To

    fun map(from: Collection<From>): Collection<To> = from.map { map(it) }
}
