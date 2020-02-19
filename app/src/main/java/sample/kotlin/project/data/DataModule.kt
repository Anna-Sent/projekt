package sample.kotlin.project.data

import dagger.Module
import sample.kotlin.project.data.providers.ProvidersModule
import sample.kotlin.project.data.repositories.RepositoriesModule
import sample.kotlin.project.data.sources.SourcesModule

@Module(
    includes = [
        ProvidersModule::class,
        RepositoriesModule::class,
        SourcesModule::class
    ]
)
interface DataModule
