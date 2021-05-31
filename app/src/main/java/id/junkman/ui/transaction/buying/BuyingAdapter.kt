package id.junkman.ui.transaction.buying

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import id.junkman.R
import id.junkman.databinding.ItemTransactionBinding
import id.junkman.model.Product

class BuyingAdapter: RecyclerView.Adapter<BuyingAdapter.ViewHolder> {
  var onItemClick: ((Product) -> Unit)? = null
  private var list = mutableListOf<Product>()

  constructor(list: ArrayList<Product>) {
    this.list = list
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
    ViewHolder(ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false))

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(list[position])
  }

  override fun getItemCount(): Int {
    return list.size
  }

  inner class ViewHolder(private val binding: ItemTransactionBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(product: Product) {
      Picasso.get().load(product.image).into(binding.imgStuff)
      binding.apply {
        txtStuff.text = product.name
        txtStatusValue.text //harusnya ini refers ke status dari transaksi
        txtStatus.setText(R.string.status)
        txtBarcode.setText(R.string.open_barcode)
        cvRoot.setOnClickListener {
          onItemClick?.invoke(product)
        }
      }
    }
  }
}