package sample.kotlin.project.data.repositories

import dagger.Module
import sample.kotlin.project.data.repositories.connectivity.ConnectivityModule

@Module(
    includes = [
        ConnectivityModule::class
    ]
)
interface RepositoriesModule
