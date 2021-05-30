package id.junkman.ui.shop

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import id.junkman.databinding.FragmentShopBinding
import id.junkman.model.Product
import id.junkman.ui.shoppingcart.CartActivity
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

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    _binding = FragmentShopBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    store = Firebase.firestore

    requestProducts()
    productSearching()

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

    adapter.onItemClick = { selectedData -> setSelectedProduct(selectedData) }
  }

  private fun setSelectedProduct(product: Product) {
    val intent = Intent(requireActivity(), CartActivity::class.java).apply {
      putExtra(CartActivity.PRODUCT, product)
    }
    startActivity(intent)
  }

  private fun productSearching() {
    binding.searchViewShop.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
      override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
          if (query.isNotEmpty()) {
            Log.d("SEARCH_QUERY", query)
            products.clear()
            adapter.filter.filter(query)
            populateProducts()
          }
        }
        return true
      }

      override fun onQueryTextChange(newText: String): Boolean {
        adapter.filter.filter(newText)
        return false
      }
    })
  }
}