package id.junkman.ui.cart

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import id.junkman.R
import id.junkman.data.source.local.entity.CartItem
import id.junkman.databinding.ActivityCartBinding
import id.junkman.model.Product
import id.junkman.ui.transaction.TransactionActivity
import id.junkman.utils.gone
import id.junkman.utils.invisible
import id.junkman.utils.visible
import org.koin.androidx.viewmodel.ext.android.viewModel

class CartActivity : AppCompatActivity() {
  private lateinit var auth: FirebaseAuth
  private lateinit var firestore: FirebaseFirestore

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

    auth = Firebase.auth
    firestore = Firebase.firestore

    setActionBar()
    adapter = ShoppingCartAdapter()
    binding.recyclerCart.layoutManager = LinearLayoutManager(this)
    binding.recyclerCart.adapter = adapter

    if (intent.hasExtra(PRODUCT)) {
      val mCartItems: ArrayList<CartItem> = ArrayList()
      val productCart = intent.getParcelableExtra<Product>(PRODUCT) as Product
      val cartItem = CartItem(
        productCart.id,
        productCart.category,
        productCart.name,
        productCart.price,
        productCart.image,
        productCart.stock,
        1,
        productCart.unit
      )
      mCartItems.add(cartItem)
      addToCart(mCartItems)
    }

    showCartItems()
  }

  private fun addToCart(cartItems: ArrayList<CartItem>) {
    viewModel.cartItems.value = cartItems
    viewModel.addToCart()
  }

  private fun showCartItems() {
    viewModel.getCartItems().observe(this, {
      if (it != null && it.isNotEmpty()) {
        cartItems = ArrayList()
        cartItems.clear()

        binding.progressBar.invisible()
        adapter.submitList(it)
        adapter.onAddBtnCartClick = { selectedCart -> addQuantity(selectedCart) }
        adapter.onMinBtnCartClick = { selectedCart -> minQuantity(selectedCart) }
        adapter.onDeleteBtnCartClick = { selectedCart -> deleteItem(selectedCart) }
        adapter.notifyDataSetChanged()

        for (cart in it) {
          cartItems.add(cart)
        }
        setConfirmButton(cartItems)
      }
    })
  }

  private fun setConfirmButton(mCartItems: ArrayList<CartItem>) {
    binding.btnOrder.setOnClickListener {
      binding.progressBar.visible()
      for ((i, cart) in mCartItems.withIndex()) {
        val data = hashMapOf(
          "category" to cart.category,
          "image" to cart.image,
          "name" to cart.name,
          "price" to (cart.price?.times(cart.quantity)),
          "quantity" to cart.quantity,
          "status" to "waiting",
          "type" to "buying",
          "unit" to cart.unit,
          "userId" to auth.currentUser!!.uid
        )
        firestore.collection("Transactions").add(data)
          .addOnSuccessListener {
            if (i == mCartItems.size - 1) {
              binding.progressBar.gone()
              viewModel.emptyCart()

              Toast.makeText(this, "Tunggu konfirmasi JunkMan, yaa!", Toast.LENGTH_SHORT)
                .show()
              val intent = Intent(this, TransactionActivity::class.java)
              startActivity(intent)
            }
          }
          .addOnFailureListener {
            binding.progressBar.gone()
            Toast.makeText(this, "Upps, ada kesalahan nih!", Toast.LENGTH_SHORT)
              .show()
          }
      }
    }
  }

  private fun addQuantity(cartItem: CartItem) {
    if (cartItem.quantity < cartItem.stock)
      viewModel.increaseItemQuantity(cartItem)
    adapter.notifyDataSetChanged()
  }

  private fun minQuantity(cartItem: CartItem) {
    if (cartItem.quantity > 1)
      viewModel.decreaseItemQuantity(cartItem)
    adapter.notifyDataSetChanged()
  }

  private fun deleteItem(cartItem: CartItem) {
    viewModel.deleteItem(cartItem)
    adapter.notifyDataSetChanged()
  }

  private fun setActionBar() {
    supportActionBar?.apply {
      setDisplayHomeAsUpEnabled(true)
      title = resources.getString(R.string.shopping_cart)
    }
  }

  private fun showBottomDialog() {
    val bottomCartFragment = BottomCartFragment()
    bottomCartFragment.show(supportFragmentManager, "checkout")
  }
}