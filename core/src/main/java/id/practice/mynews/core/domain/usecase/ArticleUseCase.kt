package id.practice.mynews.core.domain.usecase

import id.practice.mynews.core.data.Resource
import id.practice.mynews.core.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface ArticleUseCase {
    fun getArticles(): Flow<Resource<List<Article>>>
    fun getArticleByID(id: Int): Flow<Article>
    fun getFavorites(): Flow<List<Article>>
    fun setFavorite(id: Int, state: Boolean)
}