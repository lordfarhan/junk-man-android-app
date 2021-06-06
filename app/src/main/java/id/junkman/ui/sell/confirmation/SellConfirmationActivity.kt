package id.junkman.ui.sell.confirmation

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import id.junkman.databinding.ActivitySellConfirmationBinding
import id.junkman.ui.transaction.TransactionActivity
import id.junkman.utils.gone
import id.junkman.utils.visible
import java.io.ByteArrayOutputStream
import kotlin.math.roundToInt


class SellConfirmationActivity : AppCompatActivity() {
  private lateinit var storage: FirebaseStorage
  private lateinit var firestore: FirebaseFirestore
  private lateinit var auth: FirebaseAuth

  private lateinit var binding: ActivitySellConfirmationBinding
  private var name = ""
  private var price = 0
  private var image: Bitmap? = null

  private var weight = 0.0
  private var incomeEstimation = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivitySellConfirmationBinding.inflate(layoutInflater)
    setContentView(binding.root)

    storage = Firebase.storage
    firestore = Firebase.firestore
    auth = Firebase.auth

    if (intent.hasExtra(NAME) && intent.hasExtra(PRICE) && intent.hasExtra(IMAGE)) {
      name = intent.getStringExtra(NAME).toString()
      price = intent.getIntExtra(PRICE, 0)
      val uri = intent.getStringExtra(IMAGE)
      image = MediaStore.Images.Media.getBitmap(this.contentResolver, Uri.parse(uri))
    } else {
      onBackPressed()
    }

    binding.imgStuffOrder.setImageBitmap(image)
    binding.categoryStuff.text = name
    binding.txtPriceInfo.text = String.format("Rp%d", price)

    binding.txtWeightStuffPredict.addTextChangedListener(object : TextWatcher {
      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
      }

      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
      }

      override fun afterTextChanged(s: Editable?) {
        weight = if (s.toString().isNotEmpty()) {
          s.toString().toDouble()
        } else {
          0.0
        }
        getIncomeEstimation()
      }

    })

    binding.btnOrderJunkman.setOnClickListener {
      if (binding.txtWeightStuffPredict.text.isNotEmpty() && binding.txtNameStuff.text.isNotEmpty()) {
        binding.progressBarSellConfirm.visible()
        val mName = binding.txtNameStuff.text.toString()
        val data = hashMapOf(
          "category" to name.toLowerCase(),
          "image" to "",
          "name" to mName,
          "price" to incomeEstimation,
          "quantity" to weight,
          "status" to "waiting",
          "type" to "selling",
          "unit" to "kg",
          "userId" to auth.currentUser!!.uid
        )

        firestore.collection("Transactions").add(data)
          .addOnSuccessListener { documentSnapshot ->
            val storageRef =
              storage.reference.child("Transactions/" + documentSnapshot.id + ".jpeg")
            val bitmap = image
            bitmap?.let {
              val byteArrayOutputStream = ByteArrayOutputStream()
              it.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
              val imageData = byteArrayOutputStream.toByteArray()

              val uploadTask = storageRef.putBytes(imageData)
              uploadTask.addOnFailureListener {
                Toast.makeText(this, "Yahh gagal nihh, coba lagi yuk!", Toast.LENGTH_SHORT).show()
              }.addOnSuccessListener {
                firestore.collection("Transactions").document(documentSnapshot.id)
                  .update(
                    "image",
                    "https://firebasestorage.googleapis.com/v0/b/junkman-33694.appspot.com/o/Transactions%2F" + documentSnapshot.id + ".jpeg?alt=media"
                  )
                  .addOnSuccessListener {
                    binding.progressBarSellConfirm.gone()
                    Toast.makeText(this, "Tunggu Konfirmasi JunkMan, yaa!", Toast.LENGTH_SHORT)
                      .show()
                    val intent = Intent(this, TransactionActivity::class.java)
                    startActivity(intent)
                  }
                  .addOnFailureListener {
                    binding.progressBarSellConfirm.gone()
                    Toast.makeText(this, "Yahh gagal nihh, coba lagi yuk!", Toast.LENGTH_SHORT)
                      .show()
                  }
              }
            }
          }
          .addOnFailureListener {
            binding.progressBarSellConfirm.gone()
            Toast.makeText(this, "Yahh gagal nihh, coba lagi yuk!", Toast.LENGTH_SHORT).show()
          }
      } else {
        binding.progressBarSellConfirm.gone()
        Toast.makeText(this, "Lengkapi form!", Toast.LENGTH_SHORT).show()
      }
    }
  }

  private fun getIncomeEstimation() {
    incomeEstimation = (price * weight).roundToInt()
    binding.txtEstPrice.text = String.format("Rp%d", incomeEstimation)
  }

  companion object {
    const val NAME = "name"
    const val PRICE = "price"
    const val IMAGE = "image"
  }
}