package id.junkman.ui.shoppingcart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.junkman.R
import id.junkman.databinding.ActivityShoppingCartBinding

class ShoppingCartActivity : AppCompatActivity() {
  private lateinit var binding: ActivityShoppingCartBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityShoppingCartBinding.inflate(layoutInflater)
    setContentView(binding.root)

    setActionBar()

    binding.btnOrder.setOnClickListener {
      showBottomDialog()
    }
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