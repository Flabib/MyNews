package id.practice.mynews.core.utils

import id.practice.mynews.core.data.source.local.entity.ArticleEntity
import id.practice.mynews.core.data.source.remote.response.ArticleResponse
import id.practice.mynews.core.domain.model.Article

object DataMapper {

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

    fun mapDomainToEntity(input: Article) = ArticleEntity(
        input.articleId,
        input.author,
        input.content,
        input.description,
        input.publishedAt,
        input.sourceName,
        input.title,
        input.url,
        input.urlToImage
    )
}