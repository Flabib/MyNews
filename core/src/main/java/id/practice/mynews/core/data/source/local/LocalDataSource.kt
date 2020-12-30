package id.practice.mynews.core.data.source.local

import id.practice.mynews.core.data.source.local.entity.ArticleEntity
import id.practice.mynews.core.data.source.local.room.ArticleDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val articleDao: ArticleDao) {
    fun getArticles(): Flow<List<ArticleEntity>> = articleDao.getArticles()

    suspend fun insertArticles(articleList: List<ArticleEntity>) = articleDao.insertArticles(articleList)
    suspend fun clearAll() = articleDao.clearAll()
}