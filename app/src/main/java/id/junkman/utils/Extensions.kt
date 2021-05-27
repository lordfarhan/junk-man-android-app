package id.junkman.utils

import android.view.View

/**
 * @author farhan
 * created at at 18:35 on 27/05/21.
 */
fun View.visible() {
  if (visibility != View.VISIBLE) visibility = View.VISIBLE
}

fun View.gone() {
  if (visibility != View.GONE) visibility = View.GONE
}

fun View.invisible() {
  if (visibility != View.INVISIBLE) visibility = View.INVISIBLE
}