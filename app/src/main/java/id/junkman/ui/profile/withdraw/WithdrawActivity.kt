package id.junkman.ui.profile.withdraw

import android.os.Bundle
//import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
/*import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView*/
import id.junkman.R
import id.junkman.databinding.ActivityWithdrawBinding


class WithdrawActivity : AppCompatActivity() {
  private lateinit var binding: ActivityWithdrawBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityWithdrawBinding.inflate(layoutInflater)
    setContentView(binding.root)

    setActionBar()

    binding.btnSend.setOnClickListener {
      showBottomDialog()
    }

  }

  private fun setActionBar() {
    supportActionBar?.apply {
//      setDisplayHomeAsUpEnabled(true)
      title = resources.getString(R.string.withdraw)
    }
  }

  private fun showBottomDialog() {
    val bottomWithdrawFragment = BottomWithdrawFragment()
    bottomWithdrawFragment.show(supportFragmentManager, "withdraw")
  }

  override fun onDestroy() {
    super.onDestroy()
  }
}