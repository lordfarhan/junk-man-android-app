package id.junkman.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import id.junkman.R

class SplashActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_splash)

    Handler(Looper.getMainLooper()).postDelayed({
      startActivity(Intent(this@SplashActivity, LandingActivity::class.java))
      finish()
    }, 3000)
  }
}