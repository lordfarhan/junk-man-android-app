package id.junkman.ui.shoppingcart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import id.junkman.databinding.ItemShoppingCartBinding
import id.junkman.model.Product

class ShoppingCartAdapter : ListAdapter<Product, ShoppingCartAdapter.ViewHolder>(DiffCallback()) {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
    ViewHolder(ItemShoppingCartBinding.inflate(LayoutInflater.from(parent.context), parent, false))

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  class ViewHolder(private val binding: ItemShoppingCartBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(product: Product) {
      Picasso.get().load(product.image).into(binding.imgStuffCart)
      binding.apply {
        txtStuff.text = product.name
        btnMinCart.setOnClickListener {

        }
        btnAddCart.setOnClickListener {

        }
      }
    }
  }
}

class DiffCallback() : DiffUtil.ItemCallback<Product>() {
  override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
    oldItem == newItem

  override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
    oldItem.id == newItem.id
}