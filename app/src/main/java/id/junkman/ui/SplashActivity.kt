package id.junkman.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import id.junkman.R

class SplashActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_splash)

    Handler(Looper.getMainLooper()).postDelayed({
      startActivity(Intent(this@SplashActivity, WelcomeActivity::class.java))
      finish()
    }, 1500)
  }
}