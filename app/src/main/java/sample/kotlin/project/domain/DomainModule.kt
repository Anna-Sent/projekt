package sample.kotlin.project.domain

import dagger.Module
import sample.kotlin.project.data.DataModule
import sample.kotlin.project.domain.stores.StoresModule

@Module(
    includes = [
        DataModule::class,
        StoresModule::class
    ]
)
interface DomainModule
