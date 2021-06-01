package id.junkman.model

import com.google.firebase.Timestamp

/**
 * @author farhan
 * created at at 14:42 on 01/06/21.
 */
data class Balance(
  var id: String = "",
  var amount: Double = 0.0,
  var description: String = "",
  var timestamp: Timestamp? = null
)
