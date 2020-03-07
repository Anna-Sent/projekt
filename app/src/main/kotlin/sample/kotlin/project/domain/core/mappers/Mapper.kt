package sample.kotlin.project.domain.core.mappers

interface Mapper<From, To> {

    fun map(from: From): To

    fun map(from: List<From>): List<To> = from.map(::map)
}
