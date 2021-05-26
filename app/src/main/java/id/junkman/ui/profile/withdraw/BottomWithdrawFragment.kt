package id.junkman.ui.profile.withdraw

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import id.junkman.R
import id.junkman.databinding.FragmentBottomWithdrawBinding

class BottomWithdrawFragment : BottomSheetDialogFragment() {
  private var _binding: FragmentBottomWithdrawBinding? = null
  private val binding get() = _binding!!

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    _binding = FragmentBottomWithdrawBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.btnConfirmWithdraw.setOnClickListener {
      //status berubah jadi BATAL
      Toast.makeText(requireContext(), "Penarikan Diproses", Toast.LENGTH_SHORT).show()
      dismiss()
    }
    binding.btnCloseDialog.setOnClickListener {
      dismiss()
    }
  }
}