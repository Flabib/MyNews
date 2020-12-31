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
    var articleId: Int = 0,

    @ColumnInfo(name = "author")
    var author: String? = "",

    @ColumnInfo(name = "content")
    var content: String? = "",

    @ColumnInfo(name = "description")
    var description: String? = "",

    @ColumnInfo(name = "published_at")
    var publishedAt: String? = "",

    @ColumnInfo(name = "source_name")
    var sourceName: String? = "",

    @ColumnInfo(name = "title")
    var title: String? = "",

    @ColumnInfo(name = "url")
    var url: String? = "",

    @ColumnInfo(name = "url_to_image")
    var urlToImage: String? = "",

    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean? = false,
)