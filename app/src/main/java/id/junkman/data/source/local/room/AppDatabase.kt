package id.junkman.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.junkman.data.source.local.entity.CartItem

/**
 * @author farhan
 * created at at 11:18 on 29/05/21.
 */
@Database(entities = [CartItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
  abstract fun dao(): AppDao

  companion object {
    @Volatile
    private var instance: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase {
      synchronized(this) {
        var mInstance = instance

        if (mInstance == null) {
          mInstance = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "junkman_db"
          ).fallbackToDestructiveMigration().build()
          instance = mInstance
        }
        return mInstance
      }
    }
  }
}