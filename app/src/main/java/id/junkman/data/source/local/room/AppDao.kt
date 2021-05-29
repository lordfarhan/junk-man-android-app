package id.junkman.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.junkman.data.source.local.entity.CartItem

/**
 * @author farhan
 * created at at 11:19 on 29/05/21.
 */
@Dao
interface AppDao {
  @Query("SELECT * FROM cart_items")
  fun getItems(): DataSource.Factory<Int, CartItem>

  @Query("SELECT * FROM cart_items WHERE id = :id")
  fun getItemById(id: String): LiveData<CartItem>

  @Insert(entity = CartItem::class, onConflict = OnConflictStrategy.REPLACE)
  fun insertItems(items: ArrayList<CartItem>)

  @Query("DELETE FROM cart_items")
  fun deleteItems()

  @Query("DELETE FROM cart_items WHERE id = :id")
  fun deleteItemById(id: String)
}