package id.junkman.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import id.junkman.data.source.local.entity.CartItem
import id.junkman.databinding.ItemCartBinding

class ShoppingCartAdapter :
  PagedListAdapter<CartItem, ShoppingCartAdapter.ViewHolder>(DiffCallback()) {

  var onMinBtnCartClick: ((CartItem) -> Unit)? = null
  var onAddBtnCartClick: ((CartItem) -> Unit)? = null
  var onDeleteBtnCartClick: ((CartItem) -> Unit)? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
    ViewHolder(ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false))

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    getItem(position)?.let {
      holder.bind(it)
    }
  }

  inner class ViewHolder(private val binding: ItemCartBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(cartItem: CartItem) {
      Picasso.get().load(cartItem.image).into(binding.imgStuffCart)
      binding.apply {
        txtStuff.text = cartItem.name
        txtPriceStuff.text = String.format("Rp%.0f", cartItem.price)
        txtQuantityStuff.text = cartItem.quantity.toString()
        btnMinCart.setOnClickListener {
          onMinBtnCartClick?.invoke(cartItem)
        }
        btnAddCart.setOnClickListener {
          onAddBtnCartClick?.invoke(cartItem)
        }
        btnCancelCart.setOnClickListener {
          onDeleteBtnCartClick?.invoke(cartItem)
        }
      }
    }
  }
}

class DiffCallback() : DiffUtil.ItemCallback<CartItem>() {
  override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean =
    oldItem == newItem

  override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem): Boolean =
    oldItem.id == newItem.id
}