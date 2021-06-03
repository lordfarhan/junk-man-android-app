package id.junkman.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import id.junkman.databinding.FragmentProfileBinding
import id.junkman.model.Balance
import id.junkman.model.User
import id.junkman.ui.LoginActivity
import id.junkman.ui.profile.editprofile.EditProfileActivity
import id.junkman.ui.profile.historytrans.HistoryTransActivity
import id.junkman.ui.profile.withdraw.WithdrawActivity

class ProfileFragment : Fragment() {

  companion object {
    fun newInstance() = ProfileFragment()
  }

  private lateinit var viewModel: ProfileViewModel
  private var _binding: FragmentProfileBinding? = null
  private val binding get() = _binding!!
  private lateinit var auth: FirebaseAuth
  private lateinit var store: FirebaseFirestore
  private var balance = 0.0

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentProfileBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    auth = Firebase.auth
    store = Firebase.firestore

    auth.currentUser?.let {
      store.collection("Users").document(it.uid)
        .get()
        .addOnCompleteListener { task ->
          if (task.isSuccessful) {
            val user = task.result?.toObject(User::class.java)
            binding.txtName.text = user?.name
          }
        }
      binding.txtUsername.text = it.email
      it.photoUrl?.let { url ->
        Picasso.get().load(url).into(binding.imgProfile)
      }
    }

    auth.currentUser?.let {
      store.collection("Users").document(it.uid)
        .collection("Balance")
        .get()
        .addOnSuccessListener { documents ->
          for (document in documents) {
            if (document.exists()) {
              val bal: Balance = document.toObject(Balance::class.java)
              balance += bal.amount
            }
          }
          binding.txtTitle.text = String.format("Rp%.0f", balance)
        }
    }

    binding.btnEditProfile.setOnClickListener {
      val intent = Intent(requireContext(), EditProfileActivity::class.java)
      startActivity(intent)
    }
    binding.btnHistoryTrans.setOnClickListener {
      val intent = Intent(requireContext(), HistoryTransActivity::class.java)
      startActivity(intent)
    }
    binding.btnWithdraw.setOnClickListener {
      val intent = Intent(requireContext(), WithdrawActivity::class.java)
      startActivity(intent)
    }

    binding.btnLogout.setOnClickListener {
      //user logout
      auth.signOut()
      val intent = Intent(requireContext(), LoginActivity::class.java)
      startActivity(intent)
      requireActivity().finish()
    }
  }

}