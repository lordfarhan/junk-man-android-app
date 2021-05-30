package id.junkman.ui.shop

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import id.junkman.databinding.ItemProductBinding
import id.junkman.model.Product
import java.util.*

/**
 * @author farhan
 * created at at 18:55 on 28/05/21.
 */
class ShopAdapter : RecyclerView.Adapter<ShopAdapter.ViewHolder>, Filterable {
  var onItemClick: ((Product) -> Unit)? = null
  private var list = mutableListOf<Product>()
  private var filteredList = mutableListOf<Product>()

  constructor(list: ArrayList<Product>) {
    this.list = list
    this.filteredList = list
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
    ViewHolder(ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(filteredList.get(position))
  }

  override fun getItemCount(): Int {
    return filteredList.size
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

  private val customFilter = object : Filter() {
    override fun performFiltering(constraint: CharSequence?): FilterResults {
      val charString = constraint.toString()
      if (charString.isEmpty()) {
        filteredList = list
      } else {
        val mFilteredList = mutableListOf<Product>()
        for (product in list) {
          if (product.name!!.contains(charString, true)) {
            mFilteredList.add(product)
          }
        }
        filteredList = mFilteredList
      }
      val filterResults = FilterResults()
      filterResults.values = filteredList
      return filterResults
    }

    override fun publishResults(constraint: CharSequence?, filterResults: FilterResults?) {
      filteredList = filterResults?.values as MutableList<Product>
      notifyDataSetChanged()
    }
  }
}