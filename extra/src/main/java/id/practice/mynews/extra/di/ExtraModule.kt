package id.practice.mynews.extra.di

import id.practice.mynews.extra.presenter.ExtraViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val extraModule = module {
    viewModel { ExtraViewModel(get()) }
}