package id.junkman.ui.sell.confirmation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import id.junkman.databinding.ActivitySellConfirmationBinding
import id.junkman.ui.transaction.TransactionActivity

class SellConfirmationActivity : AppCompatActivity() {
  private lateinit var binding: ActivitySellConfirmationBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivitySellConfirmationBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.btnOrderJunkman.setOnClickListener {
      Toast.makeText(this, "Tunggu Konfirmasi JunkMan, yaa!", Toast.LENGTH_SHORT).show()
      val intent = Intent(this, TransactionActivity::class.java)
      startActivity(intent)
    }
  }
}