package id.junkman.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import id.junkman.data.source.local.LocalDataSource
import id.junkman.data.source.local.entity.CartItem
import id.junkman.util.AppExecutors

/**
 * @author farhan
 * created at at 11:24 on 29/05/21.
 */
class AppRepository constructor(
  private val localDataSource: LocalDataSource,
  private val appExecutors: AppExecutors
) {
  fun getItems(): LiveData<PagedList<CartItem>> {
    val config = PagedList.Config.Builder()
      .setEnablePlaceholders(false)
      .setInitialLoadSizeHint(4)
      .setPageSize(4)
      .build()
    return LivePagedListBuilder(localDataSource.getItems(), config).build()
  }

  fun addItems(items: ArrayList<CartItem>) =
    appExecutors.diskIO().execute { localDataSource.addItem(items) }

  fun removeItem(id: String) =
    appExecutors.diskIO().execute { localDataSource.deleteItemById(id) }

  fun removeItems() =
    appExecutors.diskIO().execute { localDataSource.deleteItems() }
}