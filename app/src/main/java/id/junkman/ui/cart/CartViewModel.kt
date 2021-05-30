package id.junkman.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import id.junkman.data.repository.AppRepository
import id.junkman.data.source.local.entity.CartItem

/**
 * @author farhan
 * created at at 14:28 on 29/05/21.
 */
class CartViewModel(private val repository: AppRepository) : ViewModel() {
  var cartItems: MutableLiveData<ArrayList<CartItem>> = MutableLiveData()

  fun getCartItems(): LiveData<PagedList<CartItem>> = repository.getItems()

  fun addToCart() {
    cartItems.value?.let {
      repository.addItems(it)
    }
  }

  fun increaseItemQuantity(cartItem: CartItem) {
    repository.updateItemQuantity(cartItem.id, cartItem.quantity!! + 1)
  }

  fun decreaseItemQuantity(cartItem: CartItem) {
    repository.updateItemQuantity(cartItem.id, cartItem.quantity!! - 1)
  }

}