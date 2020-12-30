package id.practice.mynews.core.data.source.remote.network

import id.practice.mynews.core.BuildConfig
import id.practice.mynews.core.data.source.remote.response.ListArticleResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines")
    suspend fun getArticles(
            @Query("country") country: String = "id",
            @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): ListArticleResponse
}
