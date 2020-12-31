package id.practice.mynews.core.domain.usecase

import id.practice.mynews.core.domain.repository.IArticleRepository

class ArticleInteractor(private val articleRepository: IArticleRepository) : ArticleUseCase {
    override fun getArticles() = articleRepository.getArticles()
    override fun getArticleByID(id: Int) = articleRepository.getArticleByID(id)
    override fun getFavorites() = articleRepository.getFavorites()
    override fun setFavorite(id: Int, state: Boolean) = articleRepository.setFavorite(id, state)
}