package id.junkman.ui.map

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.junkman.databinding.ItemMapBinding
import id.junkman.model.Office


/**
 * @author farhan
 * created at at 12:23 on 27/05/21.
 */
class MapAdapter(private val context: Context) :
  ListAdapter<Office, MapAdapter.ViewHolder>(DiffCallback()) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
    ViewHolder(ItemMapBinding.inflate(LayoutInflater.from(parent.context), parent, false))

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(context, getItem(position))
  }

  class ViewHolder(private val binding: ItemMapBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(context: Context, office: Office) {
      binding.tvName.text = office.name
      binding.tvAddress.text = office.address

      binding.tvName.setOnClickListener {
        val uri = Uri.parse("tel:" + office.phone)
        val intent = Intent(Intent.ACTION_DIAL, uri)
        try {
          context.startActivity(intent)
        } catch (s: SecurityException) {
          Toast.makeText(context, "An error occurred", Toast.LENGTH_LONG)
            .show()
        }
      }
    }
  }
}

class DiffCallback : DiffUtil.ItemCallback<Office>() {
  override fun areItemsTheSame(oldItem: Office, newItem: Office): Boolean =
    oldItem == newItem

  override fun areContentsTheSame(oldItem: Office, newItem: Office): Boolean =
    oldItem.name == newItem.name

}