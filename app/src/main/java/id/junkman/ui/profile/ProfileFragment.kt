package id.junkman.ui.profile

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.junkman.R
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

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    _binding = FragmentProfileBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

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
      val intent = Intent(requireContext(), LoginActivity::class.java)
      startActivity(intent)
    }
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
    // TODO: Use the ViewModel
  }

}