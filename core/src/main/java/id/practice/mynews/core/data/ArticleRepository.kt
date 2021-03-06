package id.practice.mynews.core.data

import id.practice.mynews.core.data.source.local.LocalDataSource
import id.practice.mynews.core.data.source.remote.RemoteDataSource
import id.practice.mynews.core.data.source.remote.network.ApiResponse
import id.practice.mynews.core.data.source.remote.response.ArticleResponse
import id.practice.mynews.core.domain.model.Article
import id.practice.mynews.core.domain.repository.IArticleRepository
import id.practice.mynews.core.utils.AppExecutors
import id.practice.mynews.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ArticleRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IArticleRepository {

    override fun getArticles(): Flow<Resource<List<Article>>> =
        object : NetworkBoundResource<List<Article>, List<ArticleResponse>>() {
            override fun loadFromDB(): Flow<List<Article>> {
                return localDataSource.getArticles().map {
                    DataMapper.mapEntitiesToDomains(it)
                }
            }

            override fun shouldFetch(data: List<Article>?): Boolean = true
//                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<ArticleResponse>>> =
                remoteDataSource.getArticles()

            override suspend fun saveCallResult(data: List<ArticleResponse>) {
                val articleList = DataMapper.mapResponsesToEntities(data)

                localDataSource.clearAll()
                localDataSource.insertArticles(articleList)
            }
        }.asFlow()

    override fun getArticleByID(id: Int): Flow<Article> {
        return localDataSource.getArticleByID(id).map {
            DataMapper.mapEntityToDomain(it)
        }
    }

    override fun getFavorites(): Flow<List<Article>> {
        return localDataSource.getFavorites().map {
            DataMapper.mapEntitiesToDomains(it)
        }
    }

    override fun setFavorite(id: Int, newState: Boolean) {
        appExecutors.diskIO().execute { localDataSource.setFavorite(id, newState) }
    }
}