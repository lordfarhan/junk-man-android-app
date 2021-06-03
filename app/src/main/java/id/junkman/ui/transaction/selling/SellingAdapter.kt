package id.junkman.ui.transaction.selling

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import id.junkman.databinding.ItemTransactionBinding
import id.junkman.model.Transaction

/**
 * @author farhan
 * created at at 13:38 on 03/06/21.
 */
class SellingAdapter : ListAdapter<Transaction, SellingAdapter.ViewHolder>(DiffCallback()) {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return ViewHolder(
      ItemTransactionBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
      )
    )
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  class ViewHolder(private val binding: ItemTransactionBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(transaction: Transaction) {
      Picasso.get().load(transaction.image).into(binding.imgStuff)
      binding.txtStuff.text = transaction.name
      binding.txtStatusValue.text = transaction.status
      binding.txtBarcode.text = transaction.id
    }
  }
}

class DiffCallback() : DiffUtil.ItemCallback<Transaction>() {
  override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
    return oldItem == newItem
  }

  override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
    return oldItem.id == newItem.id
  }

}