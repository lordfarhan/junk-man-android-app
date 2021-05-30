package id.junkman.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import henry.co.bottom.navigtion.SpaceItem
import henry.co.bottom.navigtion.SpaceOnClickListener
import id.junkman.R
import id.junkman.databinding.ActivityLandingBinding
import id.junkman.ui.transaction.TransactionActivity
import id.junkman.ui.cart.CartActivity


class LandingActivity : AppCompatActivity() {
  private lateinit var binding: ActivityLandingBinding
  private lateinit var adapter: LandingViewPagerAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityLandingBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.apply {
      spaceNavigationViewLanding.initWithSaveInstanceState(savedInstanceState)
      spaceNavigationViewLanding.addSpaceItem(
        SpaceItem("Beranda", R.drawable.ic_baseline_home_24)
      )
      spaceNavigationViewLanding.addSpaceItem(
        SpaceItem("Peta", R.drawable.ic_baseline_map_24)
      )
      spaceNavigationViewLanding.addSpaceItem(
        SpaceItem("Toko", R.drawable.ic_baseline_shopping_cart_24)
      )
      spaceNavigationViewLanding.addSpaceItem(
        SpaceItem("Profil", R.drawable.ic_baseline_person_24)
      )

      adapter = LandingViewPagerAdapter(this@LandingActivity)
      viewPager2Landing.adapter = adapter
      viewPager2Landing.isUserInputEnabled = false

      spaceNavigationViewLanding.setSpaceOnClickListener(object : SpaceOnClickListener {
        override fun onCentreButtonClick() {
        }

        override fun onItemClick(itemIndex: Int, itemName: String) {
          viewPager2Landing.currentItem = itemIndex
        }

        override fun onItemReselected(itemIndex: Int, itemName: String) {
        }

      })
    }
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_list, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.menu_list -> {
        val intent = Intent(this, TransactionActivity::class.java)
        startActivity(intent)
        true
      }
      R.id.menu_cart -> {
        val intent = Intent(this, CartActivity::class.java)
        startActivity(intent)
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    binding.spaceNavigationViewLanding.onSaveInstanceState(outState)
  }
}