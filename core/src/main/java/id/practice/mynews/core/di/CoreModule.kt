package id.practice.mynews.core.di

import androidx.room.Room
import id.practice.mynews.core.BuildConfig
import id.practice.mynews.core.data.ArticleRepository
import id.practice.mynews.core.data.source.local.LocalDataSource
import id.practice.mynews.core.data.source.local.room.ArticleDatabase
import id.practice.mynews.core.data.source.remote.RemoteDataSource
import id.practice.mynews.core.data.source.remote.network.ApiService
import id.practice.mynews.core.domain.repository.IArticleRepository
import id.practice.mynews.core.utils.AppExecutors
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<ArticleDatabase>().articleDao() }
    single {
        Room.databaseBuilder(
                androidContext(),
                ArticleDatabase::class.java, "Articles.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<IArticleRepository> {
        ArticleRepository(
                get(),
                get(),
                get()
        )
    }
}