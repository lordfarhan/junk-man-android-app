package id.junkman.model

import com.google.firebase.firestore.GeoPoint

/**
 * @author farhan
 * created at at 11:56 on 27/05/21.
 */
data class Office(
  var name: String? = "",
  var address: String? = "",
  var coordinate: GeoPoint? = null,
  var phone: String? = "",
  var type: Boolean? = true
)