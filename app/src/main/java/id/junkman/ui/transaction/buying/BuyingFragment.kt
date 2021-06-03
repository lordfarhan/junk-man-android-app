package id.junkman.ui.transaction.buying

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import id.junkman.databinding.FragmentBuyingBinding
import id.junkman.model.Product
import id.junkman.model.Transaction
import id.junkman.utils.gone
import id.junkman.utils.visible

class BuyingFragment : Fragment() {
  private var _binding: FragmentBuyingBinding? = null
  private val binding get() = _binding!!

  private lateinit var store: FirebaseFirestore
  private lateinit var adapter: BuyingAdapter
  private lateinit var transactions: ArrayList<Transaction>

  companion object {
    fun newInstance() = BuyingFragment()
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    _binding = FragmentBuyingBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    store = Firebase.firestore

    showListBuying()
  }

  private fun showListBuying() {
    binding.progressBar.visible()
    transactions = ArrayList()
    store.collection("Transactions")
      .get()
      .addOnSuccessListener { documents ->
        binding.progressBar.gone()
        for (document in documents) {
          if (document.exists()) {
            val transaction: Transaction = document.toObject(Transaction::class.java)
            transaction.id = document.id
            transactions.add(transaction)
          }
        }
        populateProducts()
      }
  }

  private fun populateProducts() {
    adapter = BuyingAdapter(transactions)
    binding.rvBuying.layoutManager = LinearLayoutManager(requireActivity())
    binding.rvBuying.adapter = adapter

    binding.progressBar.visibility = View.INVISIBLE
    adapter.onItemClick = { showBottomDialog() }
  }

  private fun showBottomDialog() {
    val myDialog = BottomDialogBuyingFragment()
    val fm: FragmentManager = childFragmentManager
    myDialog.show(fm, "buying")
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}