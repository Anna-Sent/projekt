package sample.kotlin.project.data.providers.schedulers

import dagger.Binds
import dagger.Module
import sample.kotlin.project.domain.sources.core.schedulers.SchedulersProvider
import javax.inject.Singleton

@Module
interface SchedulersModule {

    @Binds
    @Singleton
    fun bindSchedulersSource(source: SchedulersDataProvider): SchedulersProvider
}
