package id.junkman.ui.profile.withdraw

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import id.junkman.databinding.FragmentBottomWithdrawBinding

class BottomWithdrawFragment(
  private val mContext: Context,
  private val data: HashMap<String, Any>,
  private val amount: Int,
  private val adminFee: Int
) :
  BottomSheetDialogFragment() {
  private lateinit var auth: FirebaseAuth
  private lateinit var store: FirebaseFirestore
  private var _binding: FragmentBottomWithdrawBinding? = null
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentBottomWithdrawBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    auth = Firebase.auth
    store = Firebase.firestore

    val user = auth.currentUser!!
    val total = amount + adminFee

    binding.apply {
      txtAmountWithdraw.text = String.format("Rp%d", amount)
      txtAmountAdmin.text = String.format("Rp%d", adminFee)
      txtAmountTotal.text = String.format("Rp%d", total)
    }

    binding.btnConfirmWithdraw.setOnClickListener {
      //status berubah jadi BATAL
      store.collection("Withdrawals").add(data)
        .addOnSuccessListener {
          Toast.makeText(
            mContext,
            "Berhasil mengajukan, mohon tunggu informasi selanjutnya!",
            Toast.LENGTH_SHORT
          ).show()
        }
        .addOnFailureListener {
          Toast.makeText(mContext, "Upps, ada yang eror nih!", Toast.LENGTH_SHORT).show()
        }
      Toast.makeText(requireContext(), "Penarikan Diproses", Toast.LENGTH_SHORT).show()
      dismiss()
    }
    binding.btnCloseDialog.setOnClickListener {
      dismiss()
    }
  }
}