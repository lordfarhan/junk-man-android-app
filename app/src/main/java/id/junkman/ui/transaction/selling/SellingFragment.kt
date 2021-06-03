package id.junkman.ui.transaction.selling

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import id.junkman.databinding.FragmentSellingBinding
import id.junkman.model.Transaction
import id.junkman.utils.gone
import id.junkman.utils.visible

class SellingFragment : Fragment() {
  private var _binding: FragmentSellingBinding? = null
  private val binding get() = _binding!!
  private lateinit var adapter: SellingAdapter

  private lateinit var auth: FirebaseAuth
  private lateinit var firestore: FirebaseFirestore
  private var transactions: ArrayList<Transaction> = ArrayList()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentSellingBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    auth = Firebase.auth
    firestore = Firebase.firestore

    binding.progressBar.visible()
    firestore.collection("Transactions")
      .whereEqualTo("userId", auth.currentUser!!.uid)
      .whereEqualTo("type", "selling")
      .get()
      .addOnSuccessListener { documents ->
        binding.progressBar.gone()
        binding.progressBar.gone()
        for (document in documents) {
          if (document.exists()) {
            val transaction: Transaction = document.toObject(Transaction::class.java)
            transaction.id = document.id
            transactions.add(transaction)
          }
        }
        populateTransactions()
      }
      .addOnFailureListener {
        binding.progressBar.gone()
        Toast.makeText(requireContext(), "Upps, ada yang salah nih", Toast.LENGTH_SHORT).show()
      }
    //show bottom dialog harusnya pas nge klik item nya
//    showBottomDialog()
  }

  private fun populateTransactions() {
    adapter = SellingAdapter()
    binding.rvSelling.layoutManager = LinearLayoutManager(requireContext())
    binding.rvSelling.adapter = adapter
    adapter.submitList(transactions)
  }

  private fun showBottomDialog() {
    val myDialog = BottomDialogSellingFragment()
    val fm: FragmentManager = childFragmentManager
    myDialog.show(fm, "selling")
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }

}