package id.junkman.ui.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import id.junkman.R
import id.junkman.databinding.ActivityListTransactionBinding
import id.junkman.utils.SectionPagerAdapter

class ListTransactionActivity : AppCompatActivity() {
  private lateinit var binding: ActivityListTransactionBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityListTransactionBinding.inflate(layoutInflater)
    setContentView(binding.root)

    setActionBar()
    setViewPager()
  }

  private fun setViewPager() {
    val sectionsPagerAdapter = SectionPagerAdapter(this)
    val viewPager: ViewPager2 = binding.vpList
    viewPager.adapter = sectionsPagerAdapter
    val tabs: TabLayout = binding.tabLayoutList
    TabLayoutMediator(tabs, viewPager) { tab, position ->
      tab.text = resources.getString(TAB_TITLES[position])
    }.attach()
    supportActionBar?.elevation = 0f
  }

  private fun setActionBar() {
    supportActionBar?.apply {
      setDisplayHomeAsUpEnabled(true)
      title = resources.getString(R.string.list_title)
    }
  }

  companion object {
    @StringRes
    private val TAB_TITLES = intArrayOf(
      R.string.buy,
      R.string.sell
    )
  }
}