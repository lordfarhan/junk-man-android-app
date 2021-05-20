package id.junkman.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import henry.co.bottom.navigtion.SpaceItem
import id.junkman.R
import id.junkman.databinding.ActivityLandingBinding


class LandingActivity : AppCompatActivity() {
  private lateinit var binding: ActivityLandingBinding

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
    }

  }
}