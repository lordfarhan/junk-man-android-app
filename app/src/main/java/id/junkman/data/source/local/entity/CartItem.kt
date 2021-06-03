package id.junkman.data.source.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * @author farhan
 * created at at 11:14 on 29/05/21.
 */
@Entity(tableName = "cart_items")
@Parcelize
data class CartItem(
  @PrimaryKey
  var id: String = "",
  var category: String = "",
  var name: String? = "",
  var price: Double? = 0.0,
  var image: String? = "",
  var stock: Int = 0,
  var quantity: Int = 0,
  var unit: String? = ""
) : Parcelable
