package sample.kotlin.project.domain.stores

import dagger.Module
import sample.kotlin.project.domain.stores.search.SearchModule

@Module(
    includes = [
        SearchModule::class
    ]
)
interface StoresModule
