package id.practice.mynews.core.data.source.remote

import android.util.Log
import id.practice.mynews.core.data.source.remote.network.ApiResponse
import id.practice.mynews.core.data.source.remote.network.ApiService
import id.practice.mynews.core.data.source.remote.response.ArticleResponse
import id.practice.mynews.core.data.source.remote.response.MessageResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getMessages(): Flow<ApiResponse<List<MessageResponse>>> {
        return flow {
            try {
                val response = apiService.getMessages()
                val dataArray = response.result
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response.result))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getArticles(): Flow<ApiResponse<List<ArticleResponse>>> {
        return flow {
            try {
                val response = apiService.getTopHeadlines()
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

