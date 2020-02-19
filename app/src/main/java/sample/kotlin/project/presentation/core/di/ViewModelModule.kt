package sample.kotlin.project.presentation.core.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import sample.kotlin.project.presentation.core.di.ViewModelFactory

@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
