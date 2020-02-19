package sample.kotlin.project.domain.stores

import dagger.Module
import sample.kotlin.project.data.providers.schedulers.SchedulersModule
import sample.kotlin.project.domain.stores.main.MainModule
import sample.kotlin.project.domain.stores.search.SearchModule
import sample.kotlin.project.domain.stores.splash.SplashModule

@Module(
    includes = [
        SchedulersModule::class,
        MainModule::class,
        SplashModule::class,
        SearchModule::class
    ]
)
interface StoresModule
