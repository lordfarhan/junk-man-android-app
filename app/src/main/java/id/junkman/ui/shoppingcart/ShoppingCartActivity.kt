package id.junkman.ui.shoppingcart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import id.junkman.R
import id.junkman.databinding.ActivityShoppingCartBinding
import id.junkman.model.Product
import id.junkman.ui.shop.ShopAdapter

class ShoppingCartActivity : AppCompatActivity() {
  private lateinit var binding: ActivityShoppingCartBinding
  private lateinit var adapter: ShoppingCartAdapter
  private lateinit var products: ArrayList<Product>

  companion object {
    const val PRODUCT = "product"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityShoppingCartBinding.inflate(layoutInflater)
    setContentView(binding.root)

    setActionBar()

    products = ArrayList()
    val productCart = intent.getParcelableExtra<Product>(PRODUCT) as Product
    products.add(productCart)
    showProductCart(productCart)

    binding.btnOrder.setOnClickListener {
      showBottomDialog()
    }
  }

  private fun showProductCart(product: Product) {
    adapter = ShoppingCartAdapter()
    binding.recyclerCart.layoutManager = LinearLayoutManager(this)
    binding.recyclerCart.adapter = adapter
    adapter.submitList(products)
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