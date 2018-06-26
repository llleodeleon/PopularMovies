package com.leodeleon.popmovies.util

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.os.Parcelable
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
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

inline fun < reified T> Fragment.observe(data: LiveData<T>, crossinline onNull: () -> Unit = {}, crossinline onValue: (T) -> Unit ) {
  data.observe(this, Observer {
    if(it == null){
      onNull()
    } else {
      onValue(it)
    }
  })
}

inline fun <reified T: Parcelable> Fragment.getParcelableArg(key: String): T? = arguments?.getParcelable(key)