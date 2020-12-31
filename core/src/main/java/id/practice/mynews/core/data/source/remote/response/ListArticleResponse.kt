package id.practice.mynews.core.data.source.remote.response


import com.google.gson.annotations.SerializedName

data class ListArticleResponse(
    @SerializedName("articles")
    val articles: List<ArticleResponse>,
)