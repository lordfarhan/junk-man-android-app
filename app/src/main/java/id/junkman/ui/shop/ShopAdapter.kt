package id.junkman.ui.shop

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import id.junkman.databinding.ItemProductBinding
import id.junkman.model.Product
import java.util.*

/**
 * @author farhan
 * created at at 18:55 on 28/05/21.
 */
class ShopAdapter : ListAdapter<Product, ShopAdapter.ViewHolder>(DiffCallback()), Filterable {
  var onItemClick: ((Product) -> Unit)? = null
  private var list = mutableListOf<String>()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
    ViewHolder(ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  fun setData(listData: MutableList<String>){
    this.list = listData
    submitList(listData)
  }

  inner class ViewHolder(private val binding: ItemProductBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(product: Product) {
      Picasso.get().load(product.image).into(binding.imgItemPhoto)
      binding.apply {
        stuffName.text = product.name
        stuffStock.text = String.format("%s %s", product.stock.toString(), product.unit)
        stuffPrice.text = String.format("Rp%.0f/%s", product.price, product.unit)
        fabAddStuff.setOnClickListener {
          onItemClick?.invoke(product)
        }
      }
    }
  }

  override fun getFilter(): Filter {
    return customFilter
  }

  /*private val customFilter = object: Filter() {
    override fun performFiltering(constraint: CharSequence?): FilterResults {
      val filteredList = mutableListOf<Product>()
      if (constraint == null || constraint.isEmpty()) {
        filteredList.addAll(currentList)
      } else {
        val filterPattern = constraint.toString().trim { it <= ' ' }
        for (item in currentList) {
          if (item.name?.contains(filterPattern) == true) {
            filteredList.add(item)
          }
        }
      }
      val results = FilterResults()
      results.values = filteredList
      return results
    }

    override fun publishResults(constraint: CharSequence?, filterResults: FilterResults?) {
      list.clear()
      list.addAll(filterResults?.values as MutableList<Product>)
      submitList(list)
//      notifyDataSetChanged()
    }*/


    private val customFilter = object : Filter() {
      override fun performFiltering(constraint: CharSequence?): FilterResults {
        val filteredList = mutableListOf<String>()
        if (constraint == null || constraint.isEmpty()) {
          filteredList.addAll(list)
        } else {
          for (item in list) {
            if (item.startsWith(constraint.toString().toLowerCase())) {
              filteredList.add(item)
            }
          }
        }
        val results = FilterResults()
        results.values = filteredList
        return results
      }

      override fun publishResults(constraint: CharSequence?, filterResults: FilterResults?) {
        submitList(filterResults?.values as MutableList<String>)
      }
  }

}

class DiffCallback() : DiffUtil.ItemCallback<Product>() {
  override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
    oldItem == newItem

  override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
    oldItem.id == newItem.id
}