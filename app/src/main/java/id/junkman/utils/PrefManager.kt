package id.junkman.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

@SuppressLint("CommitPrefEdits")
class PrefManager(context: Context) {

  private val preferences: SharedPreferences = context
    .getSharedPreferences(PREFERENCE_NAME, PRIVATE_MODE)
  private val editor: SharedPreferences.Editor = preferences.edit()

  fun isFirstRun() = preferences.getBoolean(FIRST_TIME, true)

  fun setFirstRun() {
    editor.putBoolean(FIRST_TIME, false).commit()
    editor.commit()
  }

  companion object {
    private const val PRIVATE_MODE = 0
    private const val PREFERENCE_NAME = "configuration"
    private const val FIRST_TIME = "isFirstRun"
  }
}