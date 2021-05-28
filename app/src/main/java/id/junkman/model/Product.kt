package id.junkman.model

/**
 * @author farhan
 * created at at 18:49 on 28/05/21.
 */
data class Product(
  var id: String? = "",
  var image: String? = "",
  var name: String? = "",
  var price: Double? = 0.0,
  var stock: Int? = 0,
  var unit: String? = ""
)
