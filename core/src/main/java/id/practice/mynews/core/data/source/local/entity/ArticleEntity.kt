package id.practice.mynews.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class ArticleEntity (
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "article_id")
    var articleId: String,

    @ColumnInfo(name = "author")
    val author: String,

    @ColumnInfo(name = "content")
    val content: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "published_at")
    val publishedAt: String,

    @ColumnInfo(name = "source_name")
    val sourceName: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "url")
    val url: String,

    @ColumnInfo(name = "url_to_image")
    val urlToImage: String
)