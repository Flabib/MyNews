package id.practice.mynews

import android.app.Application
import id.practice.mynews.core.di.databaseModule
import id.practice.mynews.core.di.networkModule
import id.practice.mynews.core.di.repositoryModule
import id.practice.mynews.di.useCaseModule
import id.practice.mynews.di.viewModelModule
import id.practice.mynews.utils.ReleaseTree
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }

        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}