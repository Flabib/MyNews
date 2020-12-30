package id.practice.mynews.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.practice.mynews.core.data.source.local.entity.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Query("SELECT * FROM articles WHERE title LIKE '%' || :query || '%' ORDER BY article_id ASC")
    fun getArticles(query: String = ""): Flow<List<ArticleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<ArticleEntity>)

    @Query("DELETE FROM articles")
    suspend fun clearAll()

    @Query("SELECT * FROM articles WHERE is_favorite = 1 ORDER BY article_id ASC")
    fun getFavorites(): Flow<List<ArticleEntity>>
}