package id.junkman.ui.list.selling

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import id.junkman.R
import id.junkman.databinding.FragmentBottomDialogSellingBinding
import id.junkman.databinding.FragmentSellingBinding

class BottomDialogSellingFragment : BottomSheetDialogFragment() {

  private var _binding: FragmentBottomDialogSellingBinding? = null
  private val binding get() = _binding!!

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    _binding = FragmentBottomDialogSellingBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.btnCancelSell.setOnClickListener {
      //status berubah jadi BATAL
      Toast.makeText(requireContext(), "Pesanan Dibatalkan", Toast.LENGTH_SHORT).show()
      dismiss()
    }
    binding.btnCloseDialog.setOnClickListener {
      dismiss()
    }
  }
}