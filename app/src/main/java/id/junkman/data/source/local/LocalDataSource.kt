package id.junkman.data.source.local

import androidx.paging.DataSource
import id.junkman.data.source.local.entity.CartItem
import id.junkman.data.source.local.room.AppDao

/**
 * @author farhan
 * created at at 11:31 on 29/05/21.
 */
class LocalDataSource constructor(private val dao: AppDao) {
  fun getItems(): DataSource.Factory<Int, CartItem> = dao.getItems()

  fun addItem(items: ArrayList<CartItem>) {
    dao.insertItems(items)
  }

  fun deleteItems() {
    dao.deleteItems()
  }

  fun deleteItemById(id: String) {
    dao.deleteItemById(id)
  }
}