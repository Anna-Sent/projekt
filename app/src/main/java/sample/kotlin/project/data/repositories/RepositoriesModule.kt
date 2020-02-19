package sample.kotlin.project.data.repositories

import dagger.Module
import sample.kotlin.project.data.repositories.connectivity.ConnectivityModule
import sample.kotlin.project.data.repositories.search.SearchModule

@Module(
    includes = [
        ConnectivityModule::class,
        SearchModule::class
    ]
)
interface RepositoriesModule
