package id.junkman.ui

import android.os.Bundle
import id.junkman.R
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.github.paolorotolo.appintro.AppIntro2
import com.github.paolorotolo.appintro.AppIntro2Fragment
import com.github.paolorotolo.appintro.model.SliderPagerBuilder
import id.junkman.utils.PrefManager

class WelcomeActivity: AppIntro2() {
  private lateinit var manager: PrefManager

  @RequiresApi(Build.VERSION_CODES.M)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    manager = PrefManager(this)

    if (manager.isFirstRun()) {
      showIntroSlides()
    } else {
      goToMain()
    }
  }

  @RequiresApi(Build.VERSION_CODES.M)
  private fun showIntroSlides() {
    manager.setFirstRun()
    val pageOne = SliderPagerBuilder()
      .title(getString(R.string.title))
      .description(getString(R.string.desc1))
      .titleColor(getColor(R.color.black))
      .descColor(getColor(R.color.black))
      .imageDrawable(R.drawable.page1)
      .bgColor(getColor(R.color.white))
      .build()

    val pageTwo = SliderPagerBuilder()
      .title(getString(R.string.title))
      .description(getString(R.string.desc2))
      .titleColor(getColor(R.color.black))
      .descColor(getColor(R.color.black))
      .imageDrawable(R.drawable.page2)
      .bgColor(getColor(R.color.white))
      .build()

    val pageThree = SliderPagerBuilder()
      .title(getString(R.string.title))
      .description(getString(R.string.desc3))
      .titleColor(getColor(R.color.black))
      .descColor(getColor(R.color.black))
      .imageDrawable(R.drawable.page3)
      .bgColor(getColor(R.color.white))
      .build()

    val pageFour = SliderPagerBuilder()
      .title(getString(R.string.title))
      .description(getString(R.string.desc4))
      .titleColor(getColor(R.color.black))
      .descColor(getColor(R.color.black))
      .imageDrawable(R.drawable.page4)
      .bgColor(getColor(R.color.white))
      .build()

    addSlide(AppIntro2Fragment.newInstance(pageOne))
    addSlide(AppIntro2Fragment.newInstance(pageTwo))
    addSlide(AppIntro2Fragment.newInstance(pageThree))
    addSlide(AppIntro2Fragment.newInstance(pageFour))
    setFadeAnimation()
  }

  private fun goToMain() {
    startActivity(Intent(this, LoginActivity::class.java))
  }

  override fun onSkipPressed(currentFragment: Fragment?) {
    super.onSkipPressed(currentFragment)
    goToMain()
  }

  override fun onDonePressed(currentFragment: Fragment?) {
    super.onDonePressed(currentFragment)
    goToMain()
  }
}