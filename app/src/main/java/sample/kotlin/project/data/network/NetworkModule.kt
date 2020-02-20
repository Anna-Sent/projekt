package sample.kotlin.project.data.network

import dagger.Module
import sample.kotlin.project.data.network.http.HttpServices

@Module(
    includes = [
        HttpServices::class
    ]
)
interface NetworkModule
