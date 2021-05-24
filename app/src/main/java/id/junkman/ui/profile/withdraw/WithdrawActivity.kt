package id.junkman.ui.profile.withdraw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.junkman.R

class WithdrawActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_withdraw)

    setActionBar()
  }

  private fun setActionBar() {
    supportActionBar?.apply {
//      setDisplayHomeAsUpEnabled(true)
      title = resources.getString(R.string.withdraw)
    }
  }
}