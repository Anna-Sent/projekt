package sample.kotlin.project.domain.stores.search.middlewares

internal sealed class RequestType {

    internal object Search : RequestType()
}
