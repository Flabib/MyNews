package id.practice.mynews.core.data.source.local

import id.practice.mynews.core.data.source.local.entity.ArticleEntity
import id.practice.mynews.core.data.source.local.room.ArticleDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val articleDao: ArticleDao) {
    fun getArticles(): Flow<List<ArticleEntity>> = articleDao.getArticles()
    fun getArticleByID(id: Int): Flow<ArticleEntity> = articleDao.getArticleByID(id)
    fun getFavorites(): Flow<List<ArticleEntity>> = articleDao.getFavorites()

    fun setFavorite(id: Int, newState: Boolean) = articleDao.setFavorite(id, newState)

    suspend fun insertArticles(articleList: List<ArticleEntity>) = articleDao.insertArticles(articleList)
    suspend fun clearAll() = articleDao.clearAll()
}