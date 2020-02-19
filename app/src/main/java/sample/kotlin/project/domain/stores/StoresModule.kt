package sample.kotlin.project.domain.stores

import dagger.Module
import sample.kotlin.project.domain.stores.main.MainModule
import sample.kotlin.project.domain.stores.search.SearchModule
import sample.kotlin.project.domain.stores.splash.SplashModule

@Module(
    includes = [
        MainModule::class,
        SplashModule::class,
        SearchModule::class
    ]
)
interface StoresModule
