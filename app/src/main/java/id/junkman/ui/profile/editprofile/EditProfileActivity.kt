package id.junkman.ui.profile.editprofile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.junkman.R

class EditProfileActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_edit_profile)

    setActionBar()
  }

  private fun setActionBar() {
    supportActionBar?.apply {
//      setDisplayHomeAsUpEnabled(true)
      title = resources.getString(R.string.edit_profil)
    }
  }
}