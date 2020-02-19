package sample.kotlin.project.data.repositories.search

internal sealed class RequestType {

    internal object Search : RequestType()
}
