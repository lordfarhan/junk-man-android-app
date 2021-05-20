package id.junkman.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.junkman.ui.home.HomeFragment
import id.junkman.ui.map.MapFragment
import id.junkman.ui.profile.ProfileFragment
import id.junkman.ui.shop.ShopFragment

/**
 * @author farhan
 * created at at 0:35 on 21/05/21.
 */
class LandingViewPagerAdapter(private val activity: AppCompatActivity) :
  FragmentStateAdapter(activity) {
  override fun getItemCount(): Int = 4

  override fun createFragment(position: Int): Fragment {
    return when (position) {
      0 -> HomeFragment.newInstance()
      1 -> MapFragment.newInstance()
      2 -> ShopFragment.newInstance()
      3 -> ProfileFragment.newInstance()
      else -> Fragment()
    }
  }
}