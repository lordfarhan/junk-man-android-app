package id.junkman.ui.map

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.junkman.databinding.ItemMapBinding
import id.junkman.model.Office

/**
 * @author farhan
 * created at at 12:23 on 27/05/21.
 */
class MapAdapter : ListAdapter<Office, MapAdapter.ViewHolder>(DiffCallback()) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
    ViewHolder(ItemMapBinding.inflate(LayoutInflater.from(parent.context), parent, false))

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  class ViewHolder(private val binding: ItemMapBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(office: Office) {
      binding.tvName.text = office.name
      binding.tvPhone.text = office.phone
      binding.tvAddress.text = office.address
    }
  }
}

class DiffCallback : DiffUtil.ItemCallback<Office>() {
  override fun areItemsTheSame(oldItem: Office, newItem: Office): Boolean =
    oldItem == newItem

  override fun areContentsTheSame(oldItem: Office, newItem: Office): Boolean =
    oldItem.name == newItem.name

}