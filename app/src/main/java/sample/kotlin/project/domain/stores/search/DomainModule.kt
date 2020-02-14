package sample.kotlin.project.domain.stores.search

import dagger.Module
import sample.kotlin.project.domain.stores.StoresModule

@Module(
    includes = [
        StoresModule::class
    ]
)
interface DomainModule
