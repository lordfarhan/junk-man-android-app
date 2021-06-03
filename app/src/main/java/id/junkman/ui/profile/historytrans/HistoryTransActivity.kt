package id.junkman.ui.profile.historytrans

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import id.junkman.R
import id.junkman.databinding.ActivityHistoryTransBinding
import id.junkman.model.Balance
import id.junkman.utils.gone
import id.junkman.utils.visible

class HistoryTransActivity : AppCompatActivity() {
  private lateinit var binding: ActivityHistoryTransBinding
  private lateinit var auth: FirebaseAuth
  private lateinit var store: FirebaseFirestore
  private lateinit var balances: ArrayList<Balance>
  private lateinit var adapter: HistoryTransAdapter
  private var balance = 0.0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityHistoryTransBinding.inflate(layoutInflater)
    setContentView(binding.root)

    auth = Firebase.auth
    store = Firebase.firestore

    auth.currentUser?.let {
      binding.progressBar.visible()
      balances = ArrayList()
      store.collection("Users").document(it.uid)
        .collection("Balance")
        .get()
        .addOnSuccessListener { documents ->
          binding.progressBar.gone()
          for (document in documents) {
            if (document.exists()) {
              val bal: Balance = document.toObject(Balance::class.java)
              bal.id = document.id
              balances.add(bal)
              balance += bal.amount
            }
          }
          populateHistories()
        }
    }
    setActionBar()
  }

  private fun populateHistories() {
    binding.txtSaldo.text = String.format("Rp%.0f", balance)
    adapter = HistoryTransAdapter()
    binding.rvHistoryTrans.adapter = adapter
    adapter.submitList(balances)
  }

  private fun setActionBar() {
    supportActionBar?.apply {
//      setDisplayHomeAsUpEnabled(true)
      title = resources.getString(R.string.history_trans)
    }

  }
}