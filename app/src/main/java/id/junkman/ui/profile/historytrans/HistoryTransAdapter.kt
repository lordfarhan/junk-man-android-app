package id.junkman.ui.profile.historytrans

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.junkman.App
import id.junkman.R
import id.junkman.databinding.ItemHistoryTransBinding
import id.junkman.model.Balance
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

/**
 * @author farhan
 * created at at 14:42 on 01/06/21.
 */
class HistoryTransAdapter : ListAdapter<Balance, HistoryTransAdapter.ViewHolder>(DiffCallback()) {
  class ViewHolder(private val binding: ItemHistoryTransBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(balance: Balance) {
      binding.transRp.text = String.format("Rp%.0f", abs(balance.amount))
      if (balance.amount > 0) {
        binding.txtTypeTrans.text =
          App.getContext().resources.getString(R.string.type_trans_income)
        binding.txtTypeTrans.setTextColor(ContextCompat.getColor(App.getContext(), R.color.green))
      } else {
        binding.txtTypeTrans.text =
          App.getContext().resources.getString(R.string.type_trans_outcome)
        binding.txtTypeTrans.setTextColor(ContextCompat.getColor(App.getContext(), R.color.red))
      }
      val formatter = SimpleDateFormat("dd MMMM yyyy HH:mm", Locale.US)
      val date = balance.timestamp?.toDate()
      date?.let {
        binding.txtDateTrans.text = formatter.format(it)
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return ViewHolder(
      ItemHistoryTransBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
      )
    )
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(getItem(position))
  }
}

class DiffCallback() : DiffUtil.ItemCallback<Balance>() {
  override fun areItemsTheSame(oldItem: Balance, newItem: Balance): Boolean {
    return oldItem == newItem
  }

  override fun areContentsTheSame(oldItem: Balance, newItem: Balance): Boolean {
    return oldItem.id == newItem.id
  }

}