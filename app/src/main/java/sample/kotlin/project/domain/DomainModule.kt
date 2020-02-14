package sample.kotlin.project.domain

import dagger.Module
import sample.kotlin.project.domain.stores.StoresModule

@Module(
    includes = [
        StoresModule::class
    ]
)
interface DomainModule
