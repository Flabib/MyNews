package id.practice.mynews.core.domain.model

data class Article (
        var articleId: Int,
        val author: String? = "",
        val content: String? = "",
        val description: String? = "",
        val publishedAt: String? = "",
        val sourceName: String? = "",
        val title: String? = "",
        val url: String? = "",
        val urlToImage: String? = ""
)