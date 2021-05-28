package id.junkman.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.junkman.ui.transaction.buying.BuyingFragment
import id.junkman.ui.transaction.selling.SellingFragment

class SectionPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
  override fun getItemCount(): Int = 2

  override fun createFragment(position: Int): Fragment {
    return when (position) {
      0 -> SellingFragment()
      else -> BuyingFragment()
    }
  }
}