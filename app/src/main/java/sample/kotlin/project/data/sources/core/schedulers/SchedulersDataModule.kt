package sample.kotlin.project.data.sources.core.schedulers

import dagger.Binds
import dagger.Module
import sample.kotlin.project.domain.sources.core.schedulers.SchedulersSource
import javax.inject.Singleton

@Module
interface SchedulersDataModule {

    @Binds
    @Singleton
    fun bindSchedulersSource(source: SchedulersDataSource): SchedulersSource
}
