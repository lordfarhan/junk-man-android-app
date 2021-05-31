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
import id.junkman.utils.gone
import id.junkman.utils.visible

class BuyingFragment : Fragment() {
  private var _binding: FragmentBuyingBinding? = null
  private val binding get() = _binding!!

  private lateinit var store: FirebaseFirestore
  private lateinit var adapter: BuyingAdapter
  private lateinit var products: ArrayList<Product>

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
    products = ArrayList()
    store.collection("Products")
      .get()
      .addOnSuccessListener { documents ->
        binding.progressBar.gone()
        for ((i, document) in documents.withIndex()) {
          if (document.exists()) {
            val product: Product = document.toObject(Product::class.java)
            product.id = document.id
            products.add(product)
          }
        }
        populateProducts()
      }
  }

  private fun populateProducts() {
    adapter = BuyingAdapter(products)
    binding.rvBuying.layoutManager = LinearLayoutManager(requireActivity())
    binding.rvBuying.adapter = adapter

    binding.progressBar.visibility = View.INVISIBLE
    adapter.onItemClick = { showBottomDialog() }
  }

  private fun showBottomDialog() {
    val myDialog = BottomDialogBuyingFragment()
    val fm: FragmentManager? = fragmentManager
    if (fm != null) {
      myDialog.show(fm, "buying")
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}