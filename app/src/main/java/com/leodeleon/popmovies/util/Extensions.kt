package com.leodeleon.popmovies.util

import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
  return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}

inline fun View.snack(message: String, length: Int = Snackbar.LENGTH_SHORT, f: Snackbar.() -> Unit) {
  val snack = Snackbar.make(this, message, length)
  snack.f()
  snack.show()
}
