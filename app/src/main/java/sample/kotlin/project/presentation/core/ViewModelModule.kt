package sample.kotlin.project.presentation.core

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import sample.kotlin.project.domain.FragmentScope

@Module
interface ViewModelModule {

    @Binds
    @FragmentScope
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
