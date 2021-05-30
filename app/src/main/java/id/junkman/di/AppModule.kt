package id.junkman.di

import androidx.room.Room
import id.junkman.data.repository.AppRepository
import id.junkman.data.source.local.LocalDataSource
import id.junkman.data.source.local.room.AppDatabase
import id.junkman.ui.cart.CartViewModel
import id.junkman.util.AppExecutors
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * @author farhan
 * created at at 14:36 on 29/05/21.
 */
val viewModelModule = module {
  viewModel { CartViewModel(get()) }
}

val repositoryModule = module {
  single { LocalDataSource(get()) }
  factory { AppExecutors() }
  single {
    AppRepository(
      get(),
      get(),
    )
  }
}

val databaseModule = module {
  factory { get<AppDatabase>().dao() }
  single {
    Room.databaseBuilder(
      androidContext(),
      AppDatabase::class.java, "junkman_db"
    ).fallbackToDestructiveMigration()
      .build()
  }
}