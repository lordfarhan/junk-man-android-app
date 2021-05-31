package id.junkman.ui.transaction.buying

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import id.junkman.R
import id.junkman.databinding.ItemTransactionBinding
import id.junkman.model.Transaction

class BuyingAdapter: RecyclerView.Adapter<BuyingAdapter.ViewHolder> {
  var onItemClick: ((Transaction) -> Unit)? = null
  private var list = mutableListOf<Transaction>()

  constructor(list: ArrayList<Transaction>) {
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
    fun bind(transaction: Transaction) {
      Picasso.get().load(transaction.image).into(binding.imgStuff)
      binding.apply {
        txtStuff.text = transaction.name
        txtStatusValue.text = transaction.status
        txtStatus.setText(R.string.status)
        txtBarcode.setText(R.string.open_barcode)
        cvRoot.setOnClickListener {
          onItemClick?.invoke(transaction)
        }
      }
    }
  }
}