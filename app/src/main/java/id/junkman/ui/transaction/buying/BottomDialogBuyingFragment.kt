package id.junkman.ui.transaction.buying

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import id.junkman.databinding.FragmentBottomDialogBuyingBinding

open class BottomDialogBuyingFragment : BottomSheetDialogFragment() {

  private var _binding: FragmentBottomDialogBuyingBinding? = null
  private val binding get() = _binding!!

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    _binding = FragmentBottomDialogBuyingBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.btnCancelBuying.setOnClickListener {
      //status berubah jadi BATAL
      Toast.makeText(requireContext(), "Pesanan Dibatalkan", Toast.LENGTH_SHORT).show()
      dismiss()
    }

    binding.btnCloseDialog.setOnClickListener {
      dismiss()
    }
  }
}