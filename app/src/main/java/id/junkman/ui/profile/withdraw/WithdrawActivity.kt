package id.junkman.ui.profile.withdraw

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.RelativeLayout
//import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialog
/*import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView*/
import id.junkman.R
import id.junkman.databinding.ActivityListTransactionBinding
import id.junkman.databinding.ActivityWithdrawBinding
import id.junkman.ui.list.selling.BottomDialogSellingFragment


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