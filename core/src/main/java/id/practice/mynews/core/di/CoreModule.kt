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
import id.practice.mynews.core.utils.Tools
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
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
        val passphrase: ByteArray = SQLiteDatabase.getBytes(BuildConfig.API_KEY.toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
                androidContext(),
                ArticleDatabase::class.java, "Articles.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val networkModule = module {
    single {
        val hostname = Tools.getDomainName(BuildConfig.BASE_URL)
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/c5XTqkOxoXqc60M3HuT9fgmfeexiP2+Q8BD3+6abZYU=")
            .add(hostname, "sha256/FEzVOUp4dF3gI0ZVPRJhFbSJVXR+uQmMH65xhs1glH4=")
            .add(hostname, "sha256/Y9mvm0exBk1JoQ57f9Vm28jKo5lFm/woKcVxrYxu80o=")
            .build()
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
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