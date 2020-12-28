package id.practice.mynews.core.utils

import id.practice.mynews.core.data.source.local.entity.ArticleEntity
import id.practice.mynews.core.data.source.local.entity.MessageEntity
import id.practice.mynews.core.data.source.remote.response.ArticleResponse
import id.practice.mynews.core.data.source.remote.response.MessageResponse
import id.practice.mynews.core.domain.model.Article
import id.practice.mynews.core.domain.model.Message

object DataMapper {
    @JvmName("mapResponsesToEntitiesMessage")
    fun mapResponsesToEntities(input: List<MessageResponse>): List<MessageEntity> {
        val messageList = ArrayList<MessageEntity>()
        input.map {
            val message = MessageEntity(
                messageId = it.messageId,
                welcomeMessage = it.welcomeMessage,
            )
            messageList.add(message)
        }
        return messageList
    }

    @JvmName("mapResponsesToEntitiesArticle")
    fun mapResponsesToEntities(input: List<ArticleResponse>): List<ArticleEntity> {
        val articleList = ArrayList<ArticleEntity>()
        input.map {
            val article = ArticleEntity(
                author = it.author,
                content = it.content,
                description = it.description,
                publishedAt = it.publishedAt,
                sourceName = it.source.name,
                title = it.title,
                url = it.url,
                urlToImage = it.urlToImage
            )
            articleList.add(article)
        }
        return articleList
    }

    @JvmName("mapEntitiesToDomainMessage")
    fun mapEntitiesToDomain(input: List<MessageEntity>): List<Message> =
        input.map {
            Message(
                messageId = it.messageId,
                welcomeMessage = it.welcomeMessage,
            )
        }

    @JvmName("mapEntitiesToDomainArticle")
    fun mapEntitiesToDomain(input: List<ArticleEntity>): List<Article> =
            input.map {
                Article(
                    it.articleId,
                    it.author,
                    it.content,
                    it.description,
                    it.publishedAt,
                    it.sourceName,
                    it.title,
                    it.url,
                    it.urlToImage
                )
            }

    fun mapDomainToEntity(input: Message) = MessageEntity(
        messageId = input.messageId,
        welcomeMessage = input.welcomeMessage,
    )
}