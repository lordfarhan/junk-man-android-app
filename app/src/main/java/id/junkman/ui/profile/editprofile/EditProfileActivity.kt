package id.junkman.ui.profile.editprofile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import id.junkman.R
import id.junkman.databinding.ActivityEditProfileBinding
import id.junkman.databinding.ActivityWithdrawBinding

class EditProfileActivity : AppCompatActivity() {
  private lateinit var binding: ActivityEditProfileBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityEditProfileBinding.inflate(layoutInflater)
    setContentView(binding.root)

    setActionBar()

    binding.btnSaveProfile.setOnClickListener {
      //data di simpan
      Toast.makeText(this, "Profil Telah Diperbarui", Toast.LENGTH_SHORT).show()
    }
  }

  private fun setActionBar() {
    supportActionBar?.apply {
//      setDisplayHomeAsUpEnabled(true)
      title = resources.getString(R.string.edit_profil)
    }
  }
}