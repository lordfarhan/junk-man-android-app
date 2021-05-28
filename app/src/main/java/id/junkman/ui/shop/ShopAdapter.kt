package id.junkman.ui.shop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import id.junkman.databinding.ItemProductBinding
import id.junkman.model.Product

/**
 * @author farhan
 * created at at 18:55 on 28/05/21.
 */
class ShopAdapter : ListAdapter<Product, ShopAdapter.ViewHolder>(DiffCallback()) {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
    ViewHolder(ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  class ViewHolder(private val binding: ItemProductBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(product: Product) {
      Picasso.get().load(product.image).into(binding.imgItemPhoto)
      binding.apply {
        stuffName.text = product.name
        stuffStock.text = String.format("%s %s", product.stock.toString(), product.unit)
        stuffPrice.text = String.format("Rp%.0f/%s", product.price, product.unit)
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