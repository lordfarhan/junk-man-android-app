package id.junkman.ui.profile.withdraw

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
//import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
/*import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView*/
import id.junkman.R
import id.junkman.databinding.ActivityWithdrawBinding
import id.junkman.model.Balance

class WithdrawActivity : AppCompatActivity() {
  private lateinit var binding: ActivityWithdrawBinding
  private lateinit var auth: FirebaseAuth
  private lateinit var store: FirebaseFirestore

  private var balance = 0.0
  private var selectedAmount = 0
  private var adminFee = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityWithdrawBinding.inflate(layoutInflater)
    setContentView(binding.root)

    auth = Firebase.auth
    store = Firebase.firestore

    val user = auth.currentUser!!

    setActionBar()

    store.collection("Users").document(user.uid)
      .collection("Balance")
      .get()
      .addOnSuccessListener { documents ->
        for (document in documents) {
          if (document.exists()) {
            val bal: Balance = document.toObject(Balance::class.java)
            balance += bal.amount
          }
        }
        binding.txtSaldo.text = String.format("Rp%.0f", balance)
      }

    binding.themedToggleBtnGroupWithdrawOption.setOnSelectListener {
      when (it.id) {
        R.id.themedBtd50k -> {
          selectedAmount = 50000
          adminFee = 5000
        }
        R.id.themedBtd100k -> {
          selectedAmount = 100000
          adminFee = 4000
        }
        R.id.themedBtd200k -> {
          selectedAmount = 200000
          adminFee = 2500
        }
        else -> {
          selectedAmount = 0
          adminFee = 0
        }
      }
      binding.txtAdminFee.setText(adminFee.toString())
    }

    binding.btnSend.setOnClickListener {
//      showBottomDialog()
      val bankName = binding.txtBankName.text.toString().trim()
      val accountName = binding.txtAccountName.text.toString().trim()
      val accountNumber = binding.txtAccountNumber.text.toString().trim()

      if (selectedAmount != 0 && bankName.isNotEmpty() && accountName.isNotEmpty() && accountNumber.isNotEmpty()) {
        if (selectedAmount.toDouble() + adminFee.toDouble() > balance) {
          Toast.makeText(this, "Saldo anda tidak mencukupi!", Toast.LENGTH_SHORT).show()
        } else {
          val data: HashMap<String, Any> = hashMapOf(
            "userId" to user.uid,
            "bankName" to bankName,
            "accountName" to accountName,
            "accountNumber" to accountNumber,
            "amount" to selectedAmount,
            "adminFee" to adminFee,
            "status" to "waiting",
          )

          showBottomDialog(data)
        }
      } else {
        Toast.makeText(this, "Mohon melengkapi form.", Toast.LENGTH_SHORT).show()
      }
    }
  }

  private fun setActionBar() {
    supportActionBar?.apply {
      setDisplayHomeAsUpEnabled(true)
      title = resources.getString(R.string.withdraw)
    }
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      android.R.id.home -> onBackPressed()
    }
    return super.onOptionsItemSelected(item)
  }

  private fun showBottomDialog(data: HashMap<String, Any>) {
    val bottomWithdrawFragment = BottomWithdrawFragment(this, data, selectedAmount, adminFee)
    bottomWithdrawFragment.show(supportFragmentManager, "withdraw")
  }

}