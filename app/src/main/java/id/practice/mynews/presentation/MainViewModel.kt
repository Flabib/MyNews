package id.practice.mynews.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import id.practice.mynews.core.domain.usecase.ArticleUseCase
import id.practice.mynews.core.domain.usecase.MessageUseCase

class MainViewModel(messageUseCase: MessageUseCase, articleUseCase: ArticleUseCase) : ViewModel() {
    val message = messageUseCase.getMessage().asLiveData()
    val articles = articleUseCase.getArticles().asLiveData()
}