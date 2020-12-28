package id.practice.mynews.core.data.source.local

import id.practice.mynews.core.data.source.local.entity.ArticleEntity
import id.practice.mynews.core.data.source.local.entity.MessageEntity
import id.practice.mynews.core.data.source.local.room.ArticleDao
import id.practice.mynews.core.data.source.local.room.MessageDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val messageDao: MessageDao, private val articleDao: ArticleDao) {
    fun getMessage(): Flow<List<MessageEntity>> = messageDao.getMessage()

    suspend fun insertMessage(messageList: List<MessageEntity>) = messageDao.insertMessage(messageList)

    fun getArticles(): Flow<List<ArticleEntity>> = articleDao.getArticles()

    suspend fun insertArticles(articleList: List<ArticleEntity>) = articleDao.insertArticles(articleList)
}