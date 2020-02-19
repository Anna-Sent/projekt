package sample.kotlin.project.data.sources

import dagger.Module
import sample.kotlin.project.data.sources.request.RequestModule
import sample.kotlin.project.data.sources.search.SearchModule

@Module(
    includes = [
        RequestModule::class,
        SearchModule::class
    ]
)
interface SourcesModule
