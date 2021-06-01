package id.junkman.ui.sell

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import id.junkman.databinding.ActivitySellBinding
import id.junkman.ml.Modelbarulagi
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer

class SellActivity : AppCompatActivity() {
  private lateinit var binding: ActivitySellBinding
  private lateinit var bitmap: Bitmap

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivitySellBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val fileName = "labels.txt"
    val inputString = application.assets.open(fileName).bufferedReader().use {
      it.readText()
    }
    val townList = inputString.split("\n")

    binding.button.setOnClickListener {
      val intent: Intent = Intent(Intent.ACTION_GET_CONTENT)
      intent.type = "image/*"
      startActivityForResult(intent, 100)
    }
    binding.button3.setOnClickListener {
      val intents=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
      startActivityForResult(intents, 101)
    }

    binding.button2.setOnClickListener {
      val resized: Bitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true)
      val model = Modelbarulagi.newInstance(this)

      // Creates inputs for reference.
      val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.UINT8)
      val tbuffer = TensorImage.fromBitmap(resized)
      val byteBuffer = tbuffer.buffer
      inputFeature0.loadBuffer(byteBuffer)

      // Runs model inference and gets result.
      val outputs = model.process(inputFeature0)
      val outputFeature0 = outputs.outputFeature0AsTensorBuffer
      val max = getMax(outputFeature0.floatArray)

      binding.textView.text = townList[max]

      // Releases model resources if no longer used.
      model.close()
    }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if(requestCode==100){
      binding.imageView.setImageURI(data?.data)
      var uri: Uri?= data?.data
      bitmap=MediaStore.Images.Media.getBitmap(this.contentResolver,uri)
    }
    else if(requestCode==101){
      bitmap = data?.extras?.get("data") as Bitmap
      binding.imageView.setImageBitmap(bitmap)
    }
  }

  private fun getMax(arr: FloatArray): Int {
    var ind = 0
    var min = 0.0f
    for (i in 0..5) {
      if (arr[i] > min) {
        ind = i
        min = arr[i]
      }
    }
    return ind + 1
  }
}