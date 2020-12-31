package id.practice.mynews.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article (
    var articleId: Int,
    var author: String? = "",
    var content: String? = "",
    var description: String? = "",
    var publishedAt: String? = "",
    var sourceName: String? = "",
    var title: String? = "",
    var url: String? = "",
    var urlToImage: String? = "",
    var isFavorite: Boolean? = false,
): Parcelable