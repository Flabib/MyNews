package id.practice.mynews.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import id.practice.mynews.core.domain.usecase.ArticleUseCase

class MainViewModel(articleUseCase: ArticleUseCase) : ViewModel() {
    val articles = articleUseCase.getArticles().asLiveData()
}