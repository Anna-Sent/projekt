package sample.kotlin.project.data

import dagger.Module
import sample.kotlin.project.data.gson.GsonModule
import sample.kotlin.project.data.network.NetworkModule
import sample.kotlin.project.data.providers.ProvidersModule
import sample.kotlin.project.data.repositories.RepositoriesModule
import sample.kotlin.project.data.sources.SourcesModule

@Module(
    includes = [
        GsonModule::class,
        NetworkModule::class,
        ProvidersModule::class,
        RepositoriesModule::class,
        SourcesModule::class
    ]
)
interface DataModule
