package id.junkman.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Transaction(
  var id: String = "",
  var category: String? = "",
  var image: String? = "",
  var junkMandId: String? = "",
  var name: String? = "",
  var price: Double? = 0.0,
  var quantity: Int = 0,
  var status: String = "",
  var type: String = "",
  var unit: String? = "",
  var userId: String? = ""
  ) : Parcelable