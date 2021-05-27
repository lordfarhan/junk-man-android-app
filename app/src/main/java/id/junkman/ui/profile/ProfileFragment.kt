package id.junkman.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import id.junkman.databinding.FragmentProfileBinding
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
    auth.currentUser?.let {
      binding.txtName.text = it.displayName
      binding.txtUsername.text = it.email
      it.photoUrl?.let { url ->
        Picasso.get().load(url).into(binding.imgProfile)
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

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
    // TODO: Use the ViewModel
  }

}