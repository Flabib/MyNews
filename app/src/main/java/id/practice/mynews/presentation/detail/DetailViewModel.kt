package id.practice.mynews.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import id.practice.mynews.core.domain.usecase.ArticleUseCase

class DetailViewModel(private val articleUseCase: ArticleUseCase) : ViewModel() {
    var id: Int = 0

    fun getArticleByID() = articleUseCase.getArticleByID(id).asLiveData()

    fun setFavorite(newState: Boolean) = articleUseCase.setFavorite(id, newState)
}