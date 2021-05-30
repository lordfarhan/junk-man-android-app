package id.junkman

import android.app.Application
import android.content.Context
import id.junkman.di.databaseModule
import id.junkman.di.repositoryModule
import id.junkman.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

/**
 * @author farhan
 * created at at 14:37 on 29/05/21.
 */
class App : Application() {

  companion object {
    private lateinit var instance: App

    fun getContext(): Context {
      return instance.applicationContext
    }

  }

  override fun onCreate() {
    instance = this
    super.onCreate()

    // Start Koin
    startKoin {
      androidLogger(Level.NONE)
      androidContext(this@App)
      modules(
        viewModelModule,
        repositoryModule,
        databaseModule
      )
    }
  }
}