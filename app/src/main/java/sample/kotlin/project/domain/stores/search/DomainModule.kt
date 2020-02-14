package sample.kotlin.project.domain.stores.search

import dagger.Module

@Module(
    includes = [
        SearchModule::class
    ]
)
interface DomainModule
