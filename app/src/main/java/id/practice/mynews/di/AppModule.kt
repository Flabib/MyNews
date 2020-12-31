package id.practice.mynews.di

import id.practice.mynews.core.domain.usecase.ArticleInteractor
import id.practice.mynews.core.domain.usecase.ArticleUseCase
import id.practice.mynews.presentation.detail.DetailViewModel
import id.practice.mynews.presentation.main.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<ArticleUseCase> { ArticleInteractor(get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}