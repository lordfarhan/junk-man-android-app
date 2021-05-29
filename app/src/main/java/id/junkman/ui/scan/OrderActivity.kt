package id.junkman.ui.scan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import id.junkman.databinding.ActivityOrderBinding
import id.junkman.ui.profile.editprofile.EditProfileActivity
import id.junkman.ui.transaction.TransactionActivity

class OrderActivity : AppCompatActivity() {
  private lateinit var binding: ActivityOrderBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityOrderBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.btnOrderJunkman.setOnClickListener {
      Toast.makeText(this, "Tunggu Konfirmasi JunkMan, yaa!", Toast.LENGTH_SHORT).show()
      val intent = Intent(this, TransactionActivity::class.java)
      startActivity(intent)
    }
  }
}