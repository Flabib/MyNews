package id.practice.mynews.extra.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import id.practice.mynews.core.domain.usecase.ArticleUseCase

class ExtraViewModel(articleUseCase: ArticleUseCase) : ViewModel() {
    val articles = articleUseCase.getArticles().asLiveData()
}