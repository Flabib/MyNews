package id.practice.mynews.core.data.source.remote.network

import id.practice.mynews.core.BuildConfig
import id.practice.mynews.core.data.source.remote.response.ListArticleResponse
import id.practice.mynews.core.data.source.remote.response.ListMessageResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("hello")
    suspend fun getMessages(
            @Query("name") name: String = ""
    ): ListMessageResponse

    @GET("top-headlines")
    suspend fun getTopHeadlines(
            @Query("country") country: String = "id",
            @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): ListArticleResponse
}
