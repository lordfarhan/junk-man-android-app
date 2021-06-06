package id.junkman.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import id.junkman.databinding.FragmentBottomCartBinding

class BottomCartFragment :
  BottomSheetDialogFragment() {

  private var _binding: FragmentBottomCartBinding? = null
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentBottomCartBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.btnConfirmCart.setOnClickListener {
      //status berubah jadi BATAL
      Toast.makeText(requireContext(), "Pembelian Diproses", Toast.LENGTH_SHORT).show()
      dismiss()
    }
    binding.btnCloseDialog.setOnClickListener {
      dismiss()
    }
  }
}