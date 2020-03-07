package sample.kotlin.project.data.providers.schedulers

import dagger.Binds
import dagger.Module
import sample.kotlin.project.domain.providers.schedulers.SchedulersProvider
import javax.inject.Singleton

@Module
interface SchedulersModule {

    @Suppress("unused")
    @Binds
    @Singleton
    fun bindSchedulersProvider(source: SchedulersDataProvider): SchedulersProvider
}
