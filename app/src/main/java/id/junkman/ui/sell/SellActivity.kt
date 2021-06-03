package id.junkman.ui.sell

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import id.junkman.databinding.ActivitySellBinding
import id.junkman.ml.Junkman
import id.junkman.ui.sell.confirmation.SellConfirmationActivity
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.ByteArrayOutputStream

class SellActivity : AppCompatActivity() {
  private lateinit var binding: ActivitySellBinding
  private lateinit var bitmap: Bitmap
  private var category = ""
  private var price = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivitySellBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.fabGallery.setOnClickListener {
      val intent: Intent = Intent(Intent.ACTION_GET_CONTENT)
      intent.type = "image/*"
      startActivityForResult(intent, 100)
    }

    binding.fabCamera.setOnClickListener {
      val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
      startActivityForResult(intent, 101)
    }

    binding.fabNext.setOnClickListener {
      val bStream = ByteArrayOutputStream()
      bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream)
      val byteArray = bStream.toByteArray()

      val intent = Intent(this, SellConfirmationActivity::class.java)
      intent.putExtra(SellConfirmationActivity.NAME, category)
      intent.putExtra(SellConfirmationActivity.PRICE, price)
      intent.putExtra(SellConfirmationActivity.IMAGE, byteArray)
      startActivity(intent)
    }

    binding.textViewWrongCategory.setOnClickListener {
      binding.editTextClassCategory.requestFocus()
      val imm: InputMethodManager =
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
      imm.showSoftInput(binding.editTextClassCategory, InputMethodManager.SHOW_IMPLICIT)
    }

    binding.editTextClassCategory.addTextChangedListener(object : TextWatcher {
      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
      }

      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
      }

      override fun afterTextChanged(s: Editable?) {
        getPrice(s.toString())
      }
    })
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (requestCode == 100) {
      binding.imageViewCaptured.setImageURI(data?.data)
      val uri: Uri? = data?.data
      bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
      predict()
    } else if (requestCode == 101) {
      bitmap = data?.extras?.get("data") as Bitmap
      binding.imageViewCaptured.setImageBitmap(bitmap)
      predict()
    }
  }

  private fun predict() {
    val fileName = "labels.txt"
    val inputString = application.assets.open(fileName).bufferedReader().use {
      it.readText()
    }
    val townList = inputString.split("\n")

    val resized: Bitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true)
    val model = Junkman.newInstance(this)

    // Creates inputs for reference.
    val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.UINT8)
    val tensorBuffer = TensorImage.fromBitmap(resized)
    val byteBuffer = tensorBuffer.buffer
    inputFeature0.loadBuffer(byteBuffer)

    // Runs model inference and gets result.
    val outputs = model.process(inputFeature0)
    val outputFeature0 = outputs.outputFeature0AsTensorBuffer
    val max = getMax(outputFeature0.floatArray)

    category = townList[max].trim()
    binding.editTextClassCategory.setText(category)

    getPrice(category)

    // Releases model resources if no longer used.
    model.close()
  }

  private fun getPrice(category: String) {
    if (category.equals("Cardboard", true)) {
      price = 800
    } else if (category.equals("Glass", true)) {
      price = 1000
    } else if (category.equals("Metal", true)) {
      price = 2000
    } else if (category.equals("Paper", true)) {
      price = 1500
    } else if (category.equals("Plastic", true)) {
      price = 1500
    } else {
      price = 0
    }

    binding.editTextClassCategoryPrice.setText(price.toString())
  }

  private fun getMax(arr: FloatArray): Int {
    var ind = 0
    var min = 0.0f
    for (i in 0..4) {
      if (arr[i] > min) {
        ind = i
        min = arr[i]
      }
    }
    return ind
  }
}