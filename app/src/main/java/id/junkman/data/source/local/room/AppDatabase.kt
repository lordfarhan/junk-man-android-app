package id.junkman.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import id.junkman.data.source.local.entity.CartItem

/**
 * @author farhan
 * created at at 11:18 on 29/05/21.
 */
@Database(entities = [CartItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
  abstract fun dao(): AppDao
}