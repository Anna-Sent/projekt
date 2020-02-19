package sample.kotlin.project.data.sources.common

import dagger.Binds
import dagger.Module
import sample.kotlin.project.domain.sources.common.RequestStatusSource
import javax.inject.Singleton

@Module
interface CommonDataModule {

    @Binds
    @Singleton
    fun bindRequestStatusSource(source: RequestStatusDataSource): RequestStatusSource
}
