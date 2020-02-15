package sample.kotlin.project.domain.stores

import dagger.Module
import sample.kotlin.project.domain.stores.main.MainModule
import sample.kotlin.project.domain.stores.search.SearchModule

@Module(
    includes = [
        SearchModule::class,
        MainModule::class
    ]
)
interface StoresModule
