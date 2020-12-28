package id.practice.mynews.core.domain.usecase

import id.practice.mynews.core.domain.repository.IArticleRepository

class ArticleInteractor(private val articleRepository: IArticleRepository) : ArticleUseCase {
    override fun getArticles() = articleRepository.getArticles()
}