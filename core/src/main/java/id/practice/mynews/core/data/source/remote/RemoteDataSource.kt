package id.practice.mynews.core.data.source.remote

import android.util.Log
import id.practice.mynews.core.data.source.remote.network.ApiResponse
import id.practice.mynews.core.data.source.remote.network.ApiService
import id.practice.mynews.core.data.source.remote.response.ArticleResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getArticles(): Flow<ApiResponse<List<ArticleResponse>>> {
        return flow {
            try {
                val response = apiService.getArticles()
                val dataArray = response.articles
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response.articles))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}

