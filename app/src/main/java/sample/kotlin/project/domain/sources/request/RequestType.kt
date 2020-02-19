package sample.kotlin.project.domain.sources.request

sealed class RequestType {

    object Search : RequestType()
}
