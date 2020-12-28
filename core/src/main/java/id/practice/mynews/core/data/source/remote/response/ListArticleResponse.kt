package id.practice.mynews.core.data.source.remote.response


import com.google.gson.annotations.SerializedName

data class ListArticleResponse(
    @SerializedName("articles")
    val articles: List<ArticleResponse>,

    @SerializedName("status")
    val status: String = "",

    @SerializedName("totalResults")
    val totalResults: Int = 0
)