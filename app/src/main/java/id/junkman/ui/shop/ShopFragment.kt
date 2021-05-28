package id.junkman.ui.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import id.junkman.databinding.FragmentShopBinding
import id.junkman.model.Product
import id.junkman.utils.gone
import id.junkman.utils.visible

class ShopFragment : Fragment() {
  private var _binding: FragmentShopBinding? = null
  private val binding get() = _binding!!

  private lateinit var store: FirebaseFirestore
  private lateinit var adapter: ShopAdapter
  private lateinit var products: ArrayList<Product>

  companion object {
    fun newInstance() = ShopFragment()
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentShopBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    store = Firebase.firestore

    requestProducts()
  }

  private fun requestProducts() {
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
    adapter = ShopAdapter()
    binding.rvShop.layoutManager = GridLayoutManager(requireActivity(), 2)
    binding.rvShop.adapter = adapter
    adapter.submitList(products)
  }
}