package id.junkman.ui.profile.editprofile

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import id.junkman.R
import id.junkman.databinding.ActivityEditProfileBinding
import id.junkman.model.User

class EditProfileActivity : AppCompatActivity() {
  private lateinit var binding: ActivityEditProfileBinding
  private lateinit var auth: FirebaseAuth
  private lateinit var store: FirebaseFirestore
  private var user: User? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityEditProfileBinding.inflate(layoutInflater)
    setContentView(binding.root)

    auth = Firebase.auth
    store = Firebase.firestore

    auth.currentUser?.let {
      store.collection("Users").document(it.uid)
        .get()
        .addOnCompleteListener { task ->
          if (task.isSuccessful) {
            user = task.result?.toObject(User::class.java)
            binding.txtName.text = user?.name
            binding.txtEditName.setText(user?.name)
            binding.txtEditAddress.setText(user?.address)
          }
        }
      binding.txtUsername.text = it.email
    }

    setActionBar()

    binding.btnSaveProfile.setOnClickListener {
      //data di simpan
      if (binding.txtEditName.text.toString().isNotEmpty() &&
        binding.txtEditAddress.text.toString().isNotEmpty()
      ) {
        val data = mapOf(
          "name" to binding.txtEditName.text.toString(),
          "address" to binding.txtEditAddress.text.toString()
        )
        auth.currentUser?.let { it1 -> store.collection("Users").document(it1.uid).update(data) }
        Toast.makeText(this, "Profil telah diperbarui", Toast.LENGTH_SHORT).show()
      }
    }
  }

  private fun setActionBar() {
    supportActionBar?.apply {
//      setDisplayHomeAsUpEnabled(true)
      title = resources.getString(R.string.edit_profil)
    }
  }
}