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

class HistoryTransActivity : AppCompatActivity() {
  private lateinit var binding: ActivityHistoryTransBinding
  private lateinit var auth: FirebaseAuth
  private lateinit var store: FirebaseFirestore
  private lateinit var balances: ArrayList<Balance>
  private lateinit var adapter: HistoryTransAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityHistoryTransBinding.inflate(layoutInflater)
    setContentView(binding.root)

    auth = Firebase.auth
    store = Firebase.firestore

    auth.currentUser?.let {
      balances = ArrayList()
      store.collection("Users").document(it.uid)
        .collection("Balance")
        .get()
        .addOnSuccessListener { documents ->
          for ((i, document) in documents.withIndex()) {
            if (document.exists()) {
              val balance: Balance = document.toObject(Balance::class.java)
              balance.id = document.id
              balances.add(balance)
            }
          }
          populateHistories()
        }
    }
    setActionBar()
  }

  private fun populateHistories() {
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