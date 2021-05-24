package id.junkman.ui.profile.historytrans

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.junkman.R

class HistoryTransActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_history_trans)

    setActionBar()
  }

  private fun setActionBar() {
    supportActionBar?.apply {
//      setDisplayHomeAsUpEnabled(true)
      title = resources.getString(R.string.history_trans)
    }

  }
}