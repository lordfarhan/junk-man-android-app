package id.junkman.ui.cart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import id.junkman.R
import id.junkman.data.source.local.entity.CartItem
import id.junkman.databinding.ActivityCartBinding
import id.junkman.model.Product
import id.junkman.utils.invisible
import org.koin.androidx.viewmodel.ext.android.viewModel

class CartActivity : AppCompatActivity() {
  private lateinit var binding: ActivityCartBinding
  private lateinit var adapter: ShoppingCartAdapter
  private lateinit var cartItems: ArrayList<CartItem>
  private val viewModel: CartViewModel by viewModel()

  companion object {
    const val PRODUCT = "product"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityCartBinding.inflate(layoutInflater)
    setContentView(binding.root)

    setActionBar()
    adapter = ShoppingCartAdapter()
    binding.recyclerCart.layoutManager = LinearLayoutManager(this)
    binding.recyclerCart.adapter = adapter

    if (intent.hasExtra(PRODUCT)) {
      cartItems = ArrayList()
      val productCart = intent.getParcelableExtra<Product>(PRODUCT) as Product
      val cartItem = CartItem(
        productCart.id,
        productCart.name,
        productCart.price,
        productCart.image,
        productCart.stock,
        1,
        productCart.unit
      )
      cartItems.add(cartItem)
      addToCart()
    }

    showCartItems()

    binding.btnOrder.setOnClickListener {
      showBottomDialog()
    }
  }

  private fun addToCart() {
    viewModel.cartItems.value = cartItems
    viewModel.addToCart()
  }

  private fun showCartItems() {
    viewModel.getCartItems().observe(this, {
      if (it != null && it.isNotEmpty()) {
        binding.progressBar.invisible()
        adapter.submitList(it)
        adapter.onAddBtnCartClick = { selectedCart -> addQuantity(selectedCart) }
        adapter.onMinBtnCartClick = { selectedCart -> minQuantity(selectedCart) }
        adapter.notifyDataSetChanged()
      }
    })
  }

  private fun addQuantity(cartItem: CartItem) {
    if (cartItem.quantity < cartItem.stock)
      viewModel.increaseItemQuantity(cartItem)
  }

  private fun minQuantity(cartItem: CartItem) {
    if (cartItem.quantity > 1)
      viewModel.decreaseItemQuantity(cartItem)
  }

  private fun setActionBar() {
    supportActionBar?.apply {
      setDisplayHomeAsUpEnabled(true)
      title = resources.getString(R.string.shopping_cart)
    }
  }

  private fun showBottomDialog() {
    val bottomCartFragment = BottomCartFragment()
    bottomCartFragment.show(supportFragmentManager, "withdraw")
  }
}