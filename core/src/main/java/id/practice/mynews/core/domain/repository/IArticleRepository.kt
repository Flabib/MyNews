package id.practice.mynews.core.domain.repository

import id.practice.mynews.core.data.Resource
import id.practice.mynews.core.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface IArticleRepository {
    fun getArticles(): Flow<Resource<List<Article>>>
    fun getArticleByID(id: Int): Flow<Article>
    fun getFavorites(): Flow<List<Article>>
    fun setFavorite(id: Int, newState: Boolean)
}