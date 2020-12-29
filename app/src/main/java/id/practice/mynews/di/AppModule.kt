package id.practice.mynews.di

import id.practice.mynews.core.domain.usecase.ArticleInteractor
import id.practice.mynews.core.domain.usecase.ArticleUseCase
import id.practice.mynews.core.domain.usecase.MessageInteractor
import id.practice.mynews.core.domain.usecase.MessageUseCase
import id.practice.mynews.presentation.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<MessageUseCase> { MessageInteractor(get()) }
    factory<ArticleUseCase> { ArticleInteractor(get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get(), get()) }
}